package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.GameEndViewModel

class GameEndFragment(
    private val textResultStoryTaller:String
): BaseFragment<DataModel>() {
    override  val model: GameEndViewModel by inject()
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance(textResultStoryTaller:String) = GameEndFragment(textResultStoryTaller)
    }

    override fun iniViewModel() {
    }

    override fun init() {
        model.setTextOfStoryTaller(textResultStoryTaller)
        handlerTextOfStoryTaller()
        btn_select_cover.setOnClickListener { navigateToSelectCoverScreen() }
        //   setResumeClickListener()
    }

    fun setResumeClickListener() {
        tv_resume.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
    }
    private fun handlerTextOfStoryTaller(){
        model.subscribeOnTextOfStoryTaller().observe(viewLifecycleOwner, Observer {
            tv_tale.text=it
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