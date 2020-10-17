package ru.storytellers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rules.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.ui.BackButtonListener
import ru.terrakok.cicerone.Router

class RulesGame: Fragment(), BackButtonListener {
    private val router: Router by inject()
    companion object {
        fun newInstance() = RulesGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linesMax = resources.getInteger(R.integer.max_length_sentence)
        rules_text.text = getString(R.string.rules_full_description, linesMax)
        back_button_rules.setOnClickListener { backClicked() }

    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}