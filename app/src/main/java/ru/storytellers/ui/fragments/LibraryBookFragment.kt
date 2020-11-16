package ru.storytellers.ui.fragments

import android.view.View
import kotlinx.android.synthetic.main.fragment_library_book.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryBookViewModel

private const val FRAGMENT_DIALOG_TAG = "book-5d62-46bf-ab6"

class LibraryBookFragment(private var story: Story?) : BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book
    private var textStory: String? = null
    private var titleStory: String? = null
    private var removeStoryFlag = false

    companion object {
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        back_button_character.setOnClickListener { backToLibraryScreen() }
        btn_menu.setOnClickListener { showPopupMenu(it) }
    }

    override fun onStart() {
        super.onStart()
        story?.let { model.getTextStory(it.id) }
        story?.let { model.getTitleStory(it.name) }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun initViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, { textStoryLocal ->
            textStoryLocal?.let { text ->
                textStory = text
                tv_tale.text = text
            }
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, { titleStoryLocal ->
            titleStoryLocal?.let { title ->
                titleStory = title
                subHeader.text = title
            }
        })

        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, {
            if (it != 0) {
                context?.let { context ->
                    toastShowLong(context, context.getString(R.string.msg_delete))
                }
                story = null
                backToLibraryScreen()
            }
        })

        model.subscribeOnLocationImage().observe(viewLifecycleOwner, {
            setBackground(it)
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
            AlertDialogFragment.newInstance(this, R.string.dialog_story)
                .show(fragMan, FRAGMENT_DIALOG_TAG)
        }
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