package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.LibraryAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.LibraryViewModel

private const val FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class LibraryFragment : BaseFragment<DataModel>() {

    override val model: LibraryViewModel by inject()
    override val layoutRes = R.layout.fragment_library
    private var story: Story? = null
    private var textStory: String? = null
    private var titleStory: String? = null
    private var listStory: List<Story>? = null

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private val itemClickListener = { story: Story ->
        navigateToLibraryBookScreen(story)
    }
    private val buttonMenuClickListener = { view: View, storyLocal: Story ->
        showPopupMenu(view)
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
                                text,
                                getString(R.string.msg_share)
                            )
                        }
                    }
                    if (resultText != null) {
                        shareText(this, resultText)
                    }
                    true
                }
                R.id.btn_copy -> {
                    val res = textStory?.let { text -> copyText(requireContext(), text) }
                    if (res!!) toastShowLong(requireContext(), getString(R.string.msg_copy))
                    true
                }
                R.id.btn_delete -> {
                    removeStory()
                    true
                }
                else -> false
            }

        }
    }

    private fun getTitleStory() {
        story?.let { model.getTitleStory(it) }
    }

    private fun getTextStory() {
        story?.let { model.getTextStory(it) }
    }

    private fun removeStory() {
        activity?.supportFragmentManager?.let { fragMan ->
            story?.let { story ->
                AlertDialogFragment.newInstance(this, story)
                    .show(fragMan, FRAGMENT_DIALOG_TAG)
            }
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