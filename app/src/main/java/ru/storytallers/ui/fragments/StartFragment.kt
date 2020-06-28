package ru.storytallers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.start_button
import org.koin.android.scope.currentScope
import ru.storytallers.navigation.Screens

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override lateinit var model: StartViewModel
    private lateinit var startButton: MaterialButton

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
        startButton=start_button
        startButton.setOnClickListener{
            router.navigateTo(Screens.LevelScreen())

        }
    }

    override fun iniViewModel() {
        val viewModel: StartViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }
}