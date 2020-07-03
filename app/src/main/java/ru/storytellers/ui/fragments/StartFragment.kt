package ru.storytellers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.start_button
import org.koin.android.scope.currentScope
import ru.storytellers.navigation.Screens

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override lateinit var model: StartViewModel
    private lateinit var startButton: MaterialButton

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
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
        router.exit()
        return true
    }
}