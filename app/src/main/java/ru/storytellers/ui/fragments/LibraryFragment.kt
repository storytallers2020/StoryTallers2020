package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.LibraryAdapter
import ru.storytellers.ui.assistant.LibraryFragmentAssistant
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.LibraryViewModel


class LibraryFragment : BaseFragment<DataModel>() {

    override val model: LibraryViewModel by inject()
    private val assistant: LibraryFragmentAssistant by lazy { LibraryFragmentAssistant(this) }
    override val layoutRes = R.layout.fragment_library

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private val libraryAdapter: LibraryAdapter by lazy {
        LibraryAdapter(
            itemClickListener,
            buttonMenuClickListener,
            buttonShareClickListener,
            buttonCopyClickListener,
            buttonDeleteClickListener
        )
    }
    private val itemClickListener = { story: Story ->
        model.storySelectedStat(story)
        navigateToLibraryBookScreen(story)
    }
    private val buttonMenuClickListener = { story: Story ->
        setStoryClicked(story)
    }
    private val buttonShareClickListener = {
        model.itemShareClickedStat()
        assistant.shareText()
    }
    private val buttonCopyClickListener = {
        model.itemCopyClickedStat()
        assistant.copyText()
        toastShowLong(requireContext(), getString(R.string.msg_copy))
    }
    private val buttonDeleteClickListener = {
        model.itemDeleteClickedStat()
        assistant.showAlertDialog()
    }

    override fun init() {
        rv_books.adapter = libraryAdapter
        back_button_character.setOnClickListener {
            model.btnToStartScreenClickedStat()
            toStartScreen()
        }
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
            it.data?.let { list ->
                renderData(list)
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            activity?.let { context ->
                toastShowLong(context, context.getString(R.string.something_went_wrong))
            }
        })

        model.subscribeOnDeleteStory().observe(viewLifecycleOwner, Observer { deleted ->
            libraryAdapter.notifyDataSetChanged()
            if (deleted != 0) {
                context?.let { context ->
                    toastShowLong(context, context.getString(R.string.msg_delete))
                }
            }
        })

        model.subscribeOnChangedList().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer { storyText ->
            assistant.setStoryText(storyText)
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer { storyTitle ->
            assistant.setStoryTitle(storyTitle)
        })
    }

    private fun renderData(data: List<Story>) {
        if (data.isNotEmpty()) {
            libraryAdapter.setData(data)
        }
    }

    private fun setStoryClicked(story: Story) {
        model.setStoryLiveData(story)
    }

    fun deleteStory() {
        model.deleteStory()
    }

    private fun navigateToLibraryBookScreen(story: Story) {
        router.navigateTo(Screens.LibraryBookScreen(story))
    }

    private fun toStartScreen() {
        model.onClearStorage()
        router.newRootScreen(Screens.StartScreen())
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }
}