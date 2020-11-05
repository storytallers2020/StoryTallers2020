package ru.storytellers.ui.fragments

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
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
    private var removeStoryFlag = false
    private lateinit var backgroundView: ConstraintLayout

    private val sentencesAdapter: SentencesAdapter by lazy {
        SentencesAdapter(
            sendBtnClickListener,
            onItemSelectedListener
        )
    }

    private val sendBtnClickListener = { itemView: View, position: Int ->
        sourceSentence = sourceListSentences[position]
        sentenceStory = itemView.sentence.text.toString()
        showSaveSentenceDialog()
        Timber.e("sentenceStory = [$position] $sentenceStory")
    }

    private val titleFocusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            var newTitle = (v as TextInputEditText).text.toString()
            if (newTitle != story?.name) {
                story?.name = newTitle
                showSaveTitleDialog()
            }
            hideSoftKey(v)
            Timber.e("TITLE lost focus: ${subHeader.text}")
        }
    }

    private val onItemSelectedListener = { text: String, position: Int ->
        try {
            sentencesAdapter.notifyItemChanged(sentencePosition)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } finally {
            sentenceStory = text
            sentencePosition = position
            sentencesAdapter.selectedPosition = position
            Timber.e("RV has focus: [$sentencePosition] $sentenceStory")
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
            // Виталий, может убрать этот метод совсем? Уведомление не особо нужно.
            // Игрок никоуда не выходит и прямо там видит, что все сохранилось:
            // при переключении на другой item данные не возвращаются в исходное состояние, если схранить,
            // и возвращаются в первоначальное состояние, если не соханить и сменить фокус
            if (it) {
                Timber.i("Changes saved")
            } else {
                Timber.i("Changes don't saved")
            }
        })
        model.subscribeOnUpdateTitleStory().observe(viewLifecycleOwner, {
            if (it > 0) {
                Timber.i("Title story updated")
            } else {
                Timber.i("Title story don't updated ")
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

    fun setStateRemoveStoryFlag() {
        removeStoryFlag = true
        removeStory()
    }

    private fun removeStory() {
        story?.let { model.removeStory(it) }
        removeStoryFlag = false
    }

    private fun showDeleteDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_delete_story)
                .show(fragMan, DIALOG_TAG_DELETE)
        }
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

    private fun showSaveSentenceDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_save_story)
                .show(fragMan, DIALOG_TAG_SAVE_SENTENCE)
        }
    }

    fun saveChangedSentence() {
        val oldSentence = sourceListSentences[sentencePosition]
        story?.id?.let { storyId ->
            sentenceStory?.let { newSentence ->
                model.editSentence(storyId, oldSentence, newSentence)
                Timber.e("saving sentence: [$sentencePosition] $newSentence")
            }
        }
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