package ru.storytellers.ui.fragments

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.setTextToClipboard
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.GameEndViewModel


class GameEndFragment : BaseFragment<DataModel>() {
    override val model: GameEndViewModel by inject()
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance() = GameEndFragment()
    }

    override fun initViewModel() {}
    override fun init() {
        btn_select_cover.setOnClickListener {
            model.buttonSelectCoverClickedStat()
            navigateToSelectCoverScreen()
        }
        setResumeClickListener()
        btn_copy.setOnClickListener {
            model.buttonCopyClickedStat()
            copyText()
        }
    }

    override fun onStart() {
        super.onStart()
        model.getUriBackgroundImage()
        model.setTextOfStoryTaller()
        model.getResumeBtnVisibility()
    }

    override fun onResume() {
        super.onResume()
        handlerTextOfStoryTaller()
        handlerUriBackgroundImage()
        handlerIsResumeClicked()
    }

    private fun setResumeClickListener() {
        tv_resume.setOnClickListener {
            model.setResumeClicked()
            model.buttonContinueClickedStat()
            router.backTo(Screens.GameScreen())
        }
    }

    private fun copyText() {
        val res = tv_tale.text.toString().setTextToClipboard(requireContext())
        if (res) toastShowLong(requireContext(), getString(R.string.msg_copy))
    }

    private fun handlerTextOfStoryTaller() {
        model.subscribeOnTextOfStoryTaller().observe(viewLifecycleOwner, {
            tv_tale.text = it
        })
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnUriBackgroundImage().observe(viewLifecycleOwner, {
            setBackground(it)
        })
    }

    private fun handlerIsResumeClicked() {
        model.subscribeOnResumeClicked().observe(viewLifecycleOwner, {
            if (it) {
                tv_resume.visibility = View.GONE
            }
        })
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        // router.exit()
        return true
    }

    private fun navigateToSelectCoverScreen() {
        router.navigateTo(Screens.SelectCoverScreen())
    }

}