package ru.storytellers.ui.fragments

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
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryBookViewModel
import timber.log.Timber

const val DIALOG_TAG_DELETE = "book-delete-46bf-ab6"
const val DIALOG_TAG_SAVE_TITLE = "book-save-title-ab6"
const val DIALOG_TAG_SAVE_SENTENCE = "book-save-sentence-ab6"

class LibraryBookFragment(private var story: Story?) : BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book_edit
    private var textStory: String? = null
    private var titleStory: String? = null
    private var sentenceStory: String? = null
    private lateinit var sourceListSentences: List<SentenceOfTale>
    private lateinit var sourceSentence: SentenceOfTale
    private var sentencePosition: Int = -1
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
            sourceSentence = sourceListSentences[position]
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
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        back_button.setOnClickListener { backToLibraryScreen() }
        btn_menu.setOnClickListener { showPopupMenu(it) }
        rv_sentences.adapter = sentencesAdapter
        subHeader.onFocusChangeListener = titleFocusListener
    }

    override fun onStart() {
        super.onStart()
        story?.let {
            model.getTextStory(it.id)
            model.getTitleStory(it.name)
            backgroundView = requireActivity().findViewById(R.id.main_background)
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, {
            textStory = it
        })

        model.subscribeOnSentences().observe(viewLifecycleOwner, {
            sentencesAdapter.setData(it)
            sourceListSentences = it
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, { titleStoryLocal ->
            titleStoryLocal?.let { title ->
                titleStory = title
                subHeader.setText(title)
            }
        })

        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, {
            if (it != 0) {
                context?.let { context ->
                    toastShowShort(context, context.getString(R.string.msg_delete))
                }
                story = null
                backToLibraryScreen()
            }
        })

        model.subscribeOnLocationImage().observe(viewLifecycleOwner, {
            setBackgroundImage(it, backgroundView)
        })

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

    private fun showPopupMenu(view: View) {
        context?.let {
            CustomPopupMenu(it, view, R.menu.library)
        }?.apply {
            start()
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.btn_share -> {
                        model.itemShareClickedStat()
                        shareText()
                        true
                    }
                    R.id.btn_copy -> {
                        model.itemCopyClickedStat()
                        copyText()
                        true
                    }
                    R.id.btn_delete -> {
                        model.itemDeleteClickedStat()
                        showDeleteDialog()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun shareText() {
        with(prepareStory()) {
            if (this.isNotBlank())
                shareText(this@LibraryBookFragment, this)
        }
    }

    private fun copyText() {
        with(prepareStory()) {
            if (this.isNotBlank())
                copyText(requireContext(), this)
        }
    }

    private fun prepareStory(): String = titleStory?.let { title ->
        textStory?.let { text ->
            concatTitleAndTextStory(
                title,
                text,
                getString(
                    R.string.msg_share,
                    getString(
                        R.string.uri_to_http_google_play,
                        context?.packageName
                    )
                )
            )
        }
    }.toString()

    private fun showDeleteDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_delete_story)
                .show(fragMan, DIALOG_TAG_DELETE)
        }
    }

    fun removeStory() {
        story?.let { model.removeStory(it) }
    }

    private fun showSaveTitleDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_save_story)
                .show(fragMan, DIALOG_TAG_SAVE_TITLE)
        }
    }

    fun saveChangedTitle() {
        story?.let { model.updateTitleStory(it.name, it.id) }
    }

    fun restoreTitle() {
        subHeader.setText(titleStory)
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

    fun restoreSentence() {
        sentencesAdapter.notifyItemChanged(sentencePosition)
    }

    override fun onStop() {
        super.onStop()
        textStory = null
        titleStory = null
        loadImage(R.drawable.ic_background_default, backgroundView)
    }

    private fun backToLibraryScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }
}