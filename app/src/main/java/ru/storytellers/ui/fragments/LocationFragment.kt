package ru.storytellers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import org.koin.android.scope.currentScope
import ru.storytellers.viewmodels.LocationViewModel

class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override lateinit var model: LocationViewModel


    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
    }

    override fun iniViewModel() {
        val viewModel: LocationViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }
}