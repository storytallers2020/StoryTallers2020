package ru.storytellers.ui.fragments

import android.net.Uri
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_library_book_show.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryBookShowViewModel

const val DIALOG_TAG_DELETE = "book-delete-46bf-ab6"

class LibraryBookShowFragment(private var story: Story?) : BaseFragment<DataModel>() {
    override val model: LibraryBookShowViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book_show
    private var textStory: String? = null
    private var titleStory: String? = null
    private var uriLocationImage: Uri? = null
    private lateinit var sourceListSentences: List<SentenceOfTale>

    companion object {
        fun newInstance(story: Story) = LibraryBookShowFragment(story)
    }

    override fun init() {
        back_button.setOnClickListener { backToLibraryScreen() }
        btn_menu.setOnClickListener { showPopupMenu(it) }
    }

    override fun onStart() {
        super.onStart()
        story?.let {
            model.getTextStory(it.id)
            model.getTitleStory(it.name)
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, { textStoryLocal ->
            textStory = textStoryLocal
            text_story_field.text = textStoryLocal
        })

        model.subscribeOnSentences().observe(viewLifecycleOwner, { listSentences ->
            sourceListSentences = listSentences
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, { titleStoryLocal ->
            titleStoryLocal?.let { title ->
                titleStory = title
                sub_header.text = title
            }
        })

        model.subscribeOnLocationImage().observe(viewLifecycleOwner, { uri ->
            uriLocationImage = uri
            setBackground(uri)
        })

        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, { numberDeletedRecords ->
            if (numberDeletedRecords != 0) {
                context?.let { context ->
                    toastShowShort(context, context.getString(R.string.msg_delete))
                }
                story = null
                backToLibraryScreen()
            }
        })
    }

    private fun showPopupMenu(view: View) {
        context?.let { localContext ->
            CustomPopupMenu(localContext, view, R.menu.library)
        }?.apply {
            start()
            setOnMenuItemClickListener { menuItem ->
                processingSelectedMenuItem(menuItem)
            }
        }
    }

    private fun processingSelectedMenuItem(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.btn_edit -> {
                model.itemEditClickedStat()
                toEditStoryScreen()
                true
            }
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

    private fun toEditStoryScreen() {
        router.navigateTo(story?.let { storyLocal ->
            titleStory?.let { titleStoryLocal ->
                uriLocationImage?.let { uriLocationImg ->
                    Screens.EditingFairyTaleScreen(
                        storyLocal,
                        sourceListSentences,
                        titleStoryLocal,
                        uriLocationImg
                    )
                }
            }
        })
    }

    private fun shareText() {
        with(prepareStory()) {
            if (this.isNotBlank())
                shareText(this@LibraryBookShowFragment, this)
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

    override fun onStop() {
        super.onStop()
        textStory = null
        titleStory = null
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