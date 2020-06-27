package ru.storytallers.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.start_button
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
        startButton=start_button
        startButton.setOnClickListener{
            router.replaceScreen(Screens.LevelScreen())
        }
    }

    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }
}