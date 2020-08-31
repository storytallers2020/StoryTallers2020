package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library_book.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryBookViewModel

private const val FRAGMENT_DIALOG_TAG1 = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092396"

class LibraryBookFragment(private var story: Story?) : BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes = R.layout.fragment_library_book
    private var textStory: String? = null
    private var titleStory: String? = null
    private var removeStoryFlag =false

    companion object {
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        back_button_character.setOnClickListener { backToLibraryScreen() }
        //btn_copy.setOnClickListener { copyText() }
        //btn_share.setOnClickListener { shareText() }
        btn_copy.visibility = View.GONE
        btn_share.visibility = View.GONE
        btn_menu.setOnClickListener { showPopupMenu(it) }

    }

    override fun onStart() {
        super.onStart()
        story?.let { model.getTextStory(it.id) }
        story?.let {model.getTitleStory(it.name) }
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer { textStoryLocal ->
            textStoryLocal?.let { text ->
                textStory = text
                tv_tale.text = text
            }
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer { titleStoryLocal ->
            titleStoryLocal?.let { title ->
                titleStory = title
                subHeader.text = title
            }
        })
        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, Observer {
            if (it != 0) {
                context?.let { context ->
                    toastShowLong(context, context.getString(R.string.msg_delete))
                }
                story=null
                backToLibraryScreen()
            }
        })
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = context?.let { CustomPopupMenu(it, view) }?.apply {
            inflate(R.menu.library)
            show()
        }
        popupMenu?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_share -> {
                    val resultText = titleStory?.let { title ->
                        textStory?.let { text ->
                            concatTitleAndTextStory(
                                title,
                                text, getString(R.string.msg_share)
                            )
                        }
                    }
                    if (resultText != null) {
                        shareText(this, resultText)
                    }
                    true
                }
                R.id.btn_copy -> {
                    val res = textStory?.let { copyText(requireContext(), it) }
                    if (res!!) toastShowLong(requireContext(), getString(R.string.msg_copy))
                    true
                }
                R.id.btn_delete -> {
                    createAndShowAlertDialog()
                    true
                }
                else -> false
            }


        }
    }
    fun setStateRemoveStoryFlag(){
        removeStoryFlag=true
        removeStory()
    }

    private fun removeStory() {
        story?.let { model.removeStory(it) }
        removeStoryFlag=false
    }
    private fun createAndShowAlertDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this).show(fragMan, FRAGMENT_DIALOG_TAG1)
        }
    }

    override fun onStop() {
        super.onStop()
        textStory = null
        titleStory = null
    }

    private fun backToLibraryScreen(){
        router.backTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}