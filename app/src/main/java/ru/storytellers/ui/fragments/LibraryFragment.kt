package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.LibraryAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.LibraryViewModel

class LibraryFragment: BaseFragment<DataModel>() {

    override val model: LibraryViewModel by inject ()
    override val layoutRes = R.layout.fragment_library

    companion object {
        fun newInstance() = LibraryFragment()
    }
    private val itemClickListener = object : LibraryAdapter.ItemClickListener {
        override fun onItemClick(story: Story) {
            navigateToLibraryBookScreen(story)
        }
    }
    private val libraryAdapter: LibraryAdapter by lazy { LibraryAdapter(itemClickListener) }


    override fun init() {
        rv_books.adapter=libraryAdapter
        iniViewModel()
        model.getAllStory()
    }

    override fun iniViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.data?.let {listStoryLocal->
                if (listStoryLocal.isNotEmpty()) {
                    libraryAdapter.setData(listStoryLocal)
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            activity?.let { context -> toastShowLong(context,"Something went wrong") }
        })
    }

    private fun navigateToLibraryBookScreen(story: Story){
        router.navigateTo(Screens.LibraryBookScreen(story))
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}