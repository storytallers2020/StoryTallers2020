package ru.storytellers.ui.fragments

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
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
const val DIALOG_TAG_SAVE = "book-save-46bf-ab6"

class LibraryBookFragment(private var story: Story?) : BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book_edit
    private var textStory: String? = null
    private var titleStory: String? = null
    private var removeStoryFlag = false
    private lateinit var backgroundView: ConstraintLayout
    private lateinit var tatgetView: View
    private var position: Int = -1
    private lateinit var sourceListSentences : List<SentenceOfTale>

    private val sentencesAdapter: SentencesAdapter by lazy {
        SentencesAdapter(
            onItemClickListener,
            sentenceFocusListener
        )
    }

    private val onItemClickListener = { itemView: View, position: Int ->
        sentencesAdapter.selectedPosition = position
        sentencesAdapter.notifyDataSetChanged()
        tatgetView=itemView
        this.position=position
    }

    private val titleFocusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            // todo: тут можно сохранять название при смене фокуса
            hideSoftKey(v)
            Timber.e("TITLE lost focus")
        }
    }

    private val sentenceFocusListener = View.OnFocusChangeListener { v: View, hasFocus: Boolean ->
        if (!hasFocus) {
            // todo: тут можно сохранять измененное предложение при смене фокуса
            var newText=tatgetView.sentence.text.toString()
            var sourceSentence= sourceListSentences[position]
            model.editSentence(story?.id!!,sourceSentence,newText)
            hideSoftKey(v)
            Timber.e("RV lost focus")
        }
    }

    companion object {
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        back_button.setOnClickListener {
            // todo: если сохраняется не по 1 предложению, а вся история сразу,
            //  то тут нужна проверка, что история изменилась по сравнению с тем что было.
            //  если (есть изменения) { showSaveDialog() } else { backToLibraryScreen() }
            backToLibraryScreen()
        }
        btn_menu.setOnClickListener { showPopupMenu(it) }
        rv_sentences.adapter = sentencesAdapter
        subHeader.onFocusChangeListener = titleFocusListener
    }

    override fun onStart() {
        super.onStart()
        story?.let {
            model.getTextStory(it.id)
            model.getTitleStory(it.name)
            val asd="asd"
            backgroundView = requireActivity().findViewById(R.id.main_background)
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer {
            textStory = it
        })

        model.subscribeOnSentences().observe(viewLifecycleOwner, Observer {
            sentencesAdapter.setData(it)
            sourceListSentences=it
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer { titleStoryLocal ->
            titleStoryLocal?.let { title ->
                titleStory = title
                subHeader.setText(title)
            }
        })

        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, Observer {
            if (it != 0) {
                context?.let { context ->
                    toastShowShort(context, context.getString(R.string.msg_delete))
                }
                story = null
                backToLibraryScreen()
            }
        })

        model.subscribeOnLocationImage().observe(viewLifecycleOwner, Observer {
            setBackgroundImage(it, backgroundView)
        })

        model.subscribeOnEditSentence().observe(viewLifecycleOwner, Observer {
            if(it) {
                toastShowLong(context, "Изменения сохранены")
                sentencesAdapter.setData(sourceListSentences)
                // model.getTextStory(it.id)
            } else toastShowLong(context, "Именения не сохранены")
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
                        showAlertDialog()
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

    private fun showAlertDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_delete_story)
                .show(fragMan, DIALOG_TAG_DELETE)
        }
    }

    private fun showSaveDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_save_story)
                .show(fragMan, DIALOG_TAG_SAVE)
        }
    }

    fun saveChangedStory() {
        // todo: для сохранения сказки целиком по нажатию ОК в диалоге
        backToLibraryScreen()
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