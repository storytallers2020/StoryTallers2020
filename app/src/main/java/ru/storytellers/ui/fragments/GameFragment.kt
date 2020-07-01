package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameViewModel

class GameFragment: BaseFragment<DataModel>() {
    override lateinit var model: GameViewModel
    override val layoutRes = R.layout.fragment_game

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
        val viewModel: GameViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun init() {
        firstClickListener()
        endClickListener()
        resumeClickListener()
    }

    private fun firstClickListener() {
        layout_main.setOnClickListener {
            tv_hint.visibility = View.GONE
            layout_tale.visibility = View.VISIBLE
            et_step.visibility = View.VISIBLE
            iv_step_avatar.visibility = View.VISIBLE
            tv_step.visibility = View.VISIBLE
            tv_end.visibility = View.VISIBLE
            layout_main.setOnClickListener(null)
        }
    }

    private fun endClickListener() {
        tv_end.setOnClickListener {
            et_step.visibility = View.GONE
            iv_step_avatar.visibility = View.GONE
            tv_step.visibility = View.GONE
            tv_end.visibility = View.GONE
            tv_resume.visibility = View.VISIBLE
            btn_select_cover.visibility = View.VISIBLE
        }
    }

    private fun resumeClickListener() {
        tv_resume.setOnClickListener {
            et_step.visibility = View.VISIBLE
            iv_step_avatar.visibility = View.VISIBLE
            tv_step.visibility = View.VISIBLE
            tv_end.visibility = View.VISIBLE
            tv_resume.visibility = View.GONE
            btn_select_cover.visibility = View.GONE
        }
    }
}