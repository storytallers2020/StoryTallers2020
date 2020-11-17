package ru.storytellers.ui.fragments

import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_library_book_edit.*
import kotlinx.android.synthetic.main.item_book_sentence.view.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.SentencesAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.hideSoftKey
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.setBackgroundImage
import ru.storytellers.viewmodels.EditingFairyTaleViewModel
import timber.log.Timber

const val DIALOG_TAG_SAVE_TITLE = "book-save-title-ab6"
const val DIALOG_TAG_SAVE_SENTENCE = "book-save-sentence-ab6"

class EditingFairyTaleFragment(
    private var story: Story?,
    private var sourceListSentences: List<SentenceOfTale>?,
    private var titleStory: String?,
    private var uriLocationImage: Uri?
) : BaseFragment<DataModel>() {
    override val model: EditingFairyTaleViewModel by inject()
    override val layoutRes= R.layout.fragment_library_book_edit
    private lateinit var sourceSentence: SentenceOfTale
    private var sentencePosition: Int = -1
    private var sentenceStory: String? = null
    private lateinit var backgroundView: ConstraintLayout

    private val sentencesAdapter: SentencesAdapter by lazy {
        SentencesAdapter(
            sendBtnClickListener,
            onItemSelectedListener
        )
    }
    private val sendBtnClickListener = { itemView: View, position: Int ->
        itemView.clearFocus()
        val text = itemView.sentence.text
        Timber.e("sentenceStory = [$position] $text")
    }

    private val titleFocusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            val newTitle = subHeader.text.toString()
            if (newTitle != story?.name) {
                story?.name = newTitle
                showSaveTitleDialog()
            }
            hideSoftKey(v)
            Timber.e("TITLE lost focus: ${subHeader.text}")
        }
    }

    private val onItemSelectedListener = { view: View, position: Int, hasFocus: Boolean ->
        if (!hasFocus) {
            sentenceStory = view.sentence.text.toString()
            sentencePosition = position
            sourceListSentences?.let{listSentence ->
                sourceSentence = listSentence[position]
            }

            if(sentenceStory != sourceSentence.content) {
                showSaveSentenceDialog()
            }
            Timber.e("RV lost focus: [$position] ${sourceSentence.content} -> $sentenceStory")
        } else {
            val text = view.sentence.text.toString()
            Timber.e("RV has focus: [$position] $text")
        }
    }

    companion object {
        fun newInstance(
            story: Story,
            sourceListSentences: List<SentenceOfTale>,
            titleStory: String,
            uriLocationImage: Uri,
            ) =
            EditingFairyTaleFragment(story,sourceListSentences,titleStory,uriLocationImage)
    }

    override fun init() {
        back_button.setOnClickListener { backToLibraryBookScreen() }
        rv_sentences.adapter = sentencesAdapter
        subHeader.onFocusChangeListener = titleFocusListener
    }
    override fun onStart() {
        super.onStart()
            backgroundView = requireActivity().findViewById(R.id.main_background)
    }
    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initViewModel() {
        subHeader.setText(titleStory)
        sentencesAdapter.setData(sourceListSentences)
        uriLocationImage?.let {uriLocationImg ->
            setBackgroundImage(uriLocationImg, backgroundView) }

        model.subscribeOnEditSentence().observe(viewLifecycleOwner, {
            if (it) {
                Timber.i("Sentence updated")
            } else {
                Timber.i("Sentence update failed")
            }
        })
        model.subscribeOnUpdateTitleStory().observe(viewLifecycleOwner, {
            if (it > 0) {
                Timber.i("Title updated")
            } else {
                Timber.i("Title update failed")
            }
        })
    }

    private fun showSaveTitleDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_save_story)
                .show(fragMan, DIALOG_TAG_SAVE_TITLE)
        }
    }

    fun saveChangedTitle() {
        story?.let {
            model.updateTitleStory(it.name, it.id) }
    }

    private fun showSaveSentenceDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_save_story)
                .show(fragMan, DIALOG_TAG_SAVE_SENTENCE)
        }
    }

    fun saveChangedSentence() {
        story?.id?.let { storyId ->
            sentenceStory?.let { newSentence ->
                model.editSentence(storyId, sourceSentence, newSentence)
                Timber.e("saving sentence: [$sentencePosition] $newSentence")
            }
        }
    }

    fun restoreTitle() {
        subHeader.setText(titleStory)
    }

    fun restoreSentence() {
        sentencesAdapter.notifyItemChanged(sentencePosition)
    }

    override fun onStop() {
        super.onStop()
        story=null
        titleStory = null
        sourceListSentences = null
        uriLocationImage = null
        loadImage(R.drawable.ic_background_default, backgroundView)
    }

    private fun backToLibraryBookScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        story?.let {storyLocal ->
            router.backTo(Screens.LibraryBookScreen(storyLocal))}
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

}