package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.setBackgroundImage
import ru.storytellers.utils.setTextToClipboard
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.GameEndViewModel


class GameEndFragment : BaseFragment<DataModel>() {
    override val model: GameEndViewModel by inject()
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance() = GameEndFragment()
    }

    override fun iniViewModel() {}
    override fun init() {
        btn_select_cover.setOnClickListener { navigateToSelectCoverScreen() }
        setResumeClickListener()
        btn_copy.setOnClickListener { copyText() }
    }

    override fun onStart() {
        super.onStart()
        model.getUriBackgroundImage()
        model.setTextOfStoryTaller()
    }

    override fun onResume() {
        super.onResume()
        handlerTextOfStoryTaller()
        handlerUriBackgroundImage()
    }

    private fun setResumeClickListener() {
        tv_resume.setOnClickListener {
            router.backTo(Screens.GameScreen())
        }
    }

    private fun copyText() {
        val res = tv_tale.text.toString().setTextToClipboard(requireContext())
        if (res) toastShowLong(requireContext(), getString(R.string.text_copied))
    }

    private fun handlerTextOfStoryTaller() {
        model.subscribeOnTextOfStoryTaller().observe(viewLifecycleOwner, Observer {
            tv_tale.text = it
        })
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnUriBackgroundImage().observe(viewLifecycleOwner, Observer {
            setBackgroundImage(it, root_layout_cl)
        })
    }

    override fun backClicked(): Boolean {
       // router.exit()
        return true
    }

    private fun navigateToSelectCoverScreen() {
        router.navigateTo(Screens.SelectCoverScreen())
    }
}