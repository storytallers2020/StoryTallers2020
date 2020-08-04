package ru.storytellers.ui.fragments

import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.LibraryViewModel

class LibraryFragment(
    private val listStory:List<Story>
): BaseFragment<DataModel>() {

    override val model: LibraryViewModel by inject ()
    override val layoutRes = R.layout.fragment_library

    companion object {
        fun newInstance(listStory:List<Story>) = LibraryFragment(listStory)
    }


    override fun init() {
        TODO("Not yet implemented")
    }

    override fun iniViewModel() {
        TODO("Not yet implemented")
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}