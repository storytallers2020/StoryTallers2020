package ru.storytellers.ui.fragments

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.fragment_library_book_edit.*
import kotlinx.android.synthetic.main.fragment_library_book_edit.book_title_text
import kotlinx.android.synthetic.main.item_book_sentence.view.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.adapters.SentencesAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.hideSoftKey
import ru.storytellers.viewmodels.LibraryBookEditViewModel
import timber.log.Timber

const val DIALOG_TAG_SAVE_TITLE = "book-save-title-ab6"
const val DIALOG_TAG_SAVE_SENTENCE = "book-save-sentence-ab6"

class LibraryBookEditingFragment(
    private var story: Story?,
    private var sentencesList: List<SentenceOfTale>?,
    private var storyTitle: String?,
    private var locationImageUri: Uri?
) : BaseFragment<DataModel>() {
    override val model: LibraryBookEditViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book_edit
    private lateinit var sourceSentence: SentenceOfTale
    private var sentencePosition: Int = -1
    private var sentenceStory: String? = null

    private val sentencesAdapter: SentencesAdapter by lazy {
        SentencesAdapter(
            sendBtnClickListener,
            onItemSelectedListener
        )
    }
    private val sendBtnClickListener = { itemView: View, _: Int ->
        itemView.clearFocus()
    }

    private val titleFocusListener = View.OnFocusChangeListener { v, hasFocus ->
        isFocusingEditText()
        if (!hasFocus) {
            val newTitle = book_title_text.text.toString()
            if (newTitle != story?.name) {
                story?.name = newTitle
                showSaveTitleDialog()
            } else {
                toggleBackButton()
            }
            hideSoftKey(v)
        } else {
            toggleBackButton()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(text: Editable) {
            if (text.toString().length > 1) {
                saveChangedTitle()
                book_title_layout.isErrorEnabled = false
            } else {
                book_title_layout.error = context?.getString(R.string.enter_title)
            }
        }
    }

    private val onItemSelectedListener = { view: View, position: Int, hasFocus: Boolean ->
        isFocusingEditText()
        if (!hasFocus) {
            sentenceStory = view.sentence.text.toString()
            sentencePosition = position
            sentencesList?.let { sourceSentence = it[position] }
            if (sentenceStory != sourceSentence.content) {
                showSaveSentenceDialog()
            } else {
                toggleBackButton()
            }
        } else {
            toggleBackButton()
        }
    }

    companion object {
        fun newInstance(
            story: Story,
            sourceListSentences: List<SentenceOfTale>,
            titleStory: String,
            uriLocationImage: Uri,
        ) = LibraryBookEditingFragment(story, sourceListSentences, titleStory, uriLocationImage)
    }

    override fun init() {
        back_button.setOnClickListener { backToLibraryBookScreen() }
        with (book_title_text) {
            setText(storyTitle)
            onFocusChangeListener = titleFocusListener
            addTextChangedListener(textWatcher)
        }
        rv_sentences.adapter = sentencesAdapter.apply { setData(sentencesList) }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
        locationImageUri?.let { uri -> setBackground(uri) }
    }

    override fun initViewModel() {
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
            model.updateTitleStory(it.name, it.id)
            storyTitle = it.name
        }
        toggleBackButton()
    }

    fun restoreTitle() {
        book_title_text?.setText(storyTitle)
        storyTitle?.let { story?.name = it}
        toggleBackButton()
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
            }
        }
        toggleBackButton()
    }

    fun restoreSentence() {
        sentencesAdapter.notifyItemChanged(sentencePosition)
        toggleBackButton()
    }

    private fun isFocusingEditText() : Boolean {
        val focusedViewName = view?.findFocus()?.javaClass?.simpleName ?: null.toString()
        return focusedViewName.contains(getString(R.string.edit_text_view_name))
    }

    private fun toggleBackButton() {
        with(back_button) {
            isClickable = !isFocusingEditText()
            isEnabled = !isFocusingEditText()
        }
    }

    private fun backToLibraryBookScreen() {
        backClicked()
    }

    override fun backClicked(): Boolean {
        if (back_button.isClickable) {
            model.onBackClicked(this.javaClass.simpleName)
            router.exit()
        } else {
            view?.clearFocus()
        }
        return true
    }

}