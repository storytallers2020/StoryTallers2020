package ru.storytellers.ui.fragments

import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.SelectCoverViewModel

class SelectCoverFragment: BaseFragment<DataModel>() {
    override val model: SelectCoverViewModel by inject()
    override val layoutRes= R.layout.fragment_choosing_cover
    companion object {
        fun newInstance() = SelectCoverFragment()
    }


    override fun init() {
        TODO("Not yet implemented")
    }

    override fun iniViewModel() {
        TODO("Not yet implemented")
    }

    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }
}