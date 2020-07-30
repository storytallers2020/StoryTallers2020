package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.GameEndViewModel

class GameEndFragment(
    private val textResultStoryTaller:String
): BaseFragment<DataModel>() {
    override  val model: GameEndViewModel by inject()
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance(textResultStoryTaller:String) = GameEndFragment(textResultStoryTaller)
    }

    override fun iniViewModel() {}
    override fun init() {
        model.getUriBackgroundImage()
        model.setTextOfStoryTaller(textResultStoryTaller)
        handlerTextOfStoryTaller()
        handlerUriBackgroundImage()
        btn_select_cover.setOnClickListener { navigateToSelectCoverScreen() }
        setResumeClickListener()
    }

    private fun setResumeClickListener() {
        tv_resume.setOnClickListener {
            router.backTo(Screens.GameScreen())
        }
    }

    private fun handlerTextOfStoryTaller(){
        model.subscribeOnTextOfStoryTaller().observe(viewLifecycleOwner, Observer {
            tv_tale.text=it
        })
    }

    private fun handlerUriBackgroundImage(){
        model.subscribeOnUriBackgroundImage().observe(viewLifecycleOwner, Observer {
            setBackgroundImage(it, root_layout_cl)
        })
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun navigateToSelectCoverScreen() {
         router.navigateTo(Screens.SelectCoverScreen())
    }
}