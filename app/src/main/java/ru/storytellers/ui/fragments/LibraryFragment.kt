package ru.storytellers.ui.fragments

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.item_book_library.view.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.LibraryAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryViewModel

private const val FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092397"

class LibraryFragment : BaseFragment<DataModel>() {

    override val model: LibraryViewModel by inject()
    override val layoutRes = R.layout.fragment_library
    private var story: Story? = null
    private var textStory: String? = null
    private var titleStory: String? = null
    private var listStory: List<Story>? = null
    private var removeStoryFlag =false

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private val itemClickListener = { story: Story ->
        navigateToLibraryBookScreen(story)
    }
    private val buttonMenuClickListener = { view: View, storyLocal: Story ->
        togglePopupMenu(view)
        story = storyLocal
        getTitleStory()
        getTextStory()
    }

    private val libraryAdapter: LibraryAdapter by lazy {
        LibraryAdapter(
            itemClickListener,
            buttonMenuClickListener
        )
    }

    override fun init() {
        rv_books.adapter = libraryAdapter
        back_button_character.setOnClickListener { toStartScreen() }
    }

    override fun onStart() {
        super.onStart()
        model.getAllStory()
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.data?.let { listStoryLocal ->
                if (listStoryLocal.isNotEmpty()) {
                    listStory = listStoryLocal
                    libraryAdapter.setData(listStory)
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            activity?.let { context ->
                toastShowLong(
                    context,
                    context.getString(R.string.something_went_wrong)
                )
            }
        })

        model.subscribeOnRemoveStory().observe(viewLifecycleOwner, Observer {deleted ->
            if (deleted != 0) {
                context?.let { context ->
                    toastShowLong(context, context.getString(R.string.msg_delete))
                }
                listStory?.let {
                    val changedList = it.toMutableList()
                    changedList.remove(story)
                    libraryAdapter.setData(changedList)
                }
            }
        })
        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer { textStoryLocal ->
            textStory = textStoryLocal
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer { titleStoryLocal ->
            titleStory = titleStoryLocal
        })
    }

    private fun togglePopupMenu(view: View) {
        with(view.popup_menu) {
            setMenuItemsClickListeners(view)
            visibility = if(visibility == GONE) VISIBLE else GONE
        }
    }

    private fun setMenuItemsClickListeners(view: View) {
        with(view) {
            btn_share.setOnClickListener {
                titleStory?.let { title ->
                    textStory?.let { text ->
                        with(concatTitleAndTextStory(title, text, getString(R.string.msg_share))) {
                            if (this.isNotBlank()) shareText(this@LibraryFragment, this)
                        }
                    }
                }
            }
            btn_copy.setOnClickListener {
                textStory?.let { text ->
                    copyText(requireContext(), text)
                    toastShowLong(requireContext(), getString(R.string.msg_copy))
                }
            }
            btn_delete.setOnClickListener {
                createAndShowAlertDialog()
            }
        }
    }

    fun setStateRemoveStoryFlag(){
        removeStoryFlag=true
        removeStory()
    }

    private fun getTitleStory() {
        story?.let { model.getTitleStory(it) }
    }

    private fun getTextStory() {
        story?.let { model.getTextStory(it) }
    }

    private fun removeStory() {
        story?.let { model.removeStory(it) }
        removeStoryFlag=false
    }

    private fun createAndShowAlertDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this, R.string.dialog_story).show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }

    private fun navigateToLibraryBookScreen(story: Story) {
        router.navigateTo(Screens.LibraryBookScreen(story))
    }

    private fun toStartScreen() {
        model.onClearStorage()
        router.newRootScreen(Screens.StartScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}