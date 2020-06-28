package ru.storytallers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import org.koin.android.scope.currentScope
import ru.storytallers.viewmodels.LocationViewModel

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