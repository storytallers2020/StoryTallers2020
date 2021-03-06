package ru.storytellers.ui.fragments

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_start.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.setBackgroundImage
import ru.storytellers.viewmodels.GameStartViewModel

class GameStartFragment : BaseFragment<DataModel>() {
    override val model: GameStartViewModel by inject()
    override val layoutRes = R.layout.fragment_game_start
    private lateinit var backgroundView: ConstraintLayout

    companion object {
        fun newInstance() = GameStartFragment()
    }

    private fun navigateToGameScreen() {
        router.navigateTo(Screens.GameScreen())
    }

    override fun init() {
        iniViewModel()
        handlerUriBackgroundImage()
        back_button_location.setOnClickListener { backToLocationScreen() }
    }

    override fun iniViewModel() {
        btn_next.setOnClickListener {
            model.createNewGame()
            model.buttonStartClickedStatistic()
            navigateToGameScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        model.getUriBackgroundImage()
        model.requestGameLevelFromStorage()
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnBackgroundImageChanged().observe(viewLifecycleOwner, Observer {
            backgroundView = requireActivity().findViewById(R.id.main_background)
            setBackgroundImage(it, backgroundView)
        })
    }

    override fun onResume() {
        super.onResume()
        model.subscribeOnLevelGame().observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> {
                    getRuleFromResources(R.string.rules_easy_description)
                }
                1 -> {
                    getRuleFromResources(R.string.rules_medium_description)
                }
                else -> {
                    getRuleFromResources(R.string.rules_hard_description)
                }
            }
        })
    }

    private fun getRuleFromResources(stringId: Int?) {
        val rule = stringId?.let { context?.getString(it) }
        setRule(rule)
    }

    private fun setRule(rule: String?) {
        rule?.let { text_rule_tv.text = it }
    }

    private fun backToLocationScreen() {
        setDefaultBackground()
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.LocationScreen())
    }

    override fun backClicked(): Boolean {
        setDefaultBackground()
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

    private fun setDefaultBackground() {
        loadImage(R.drawable.ic_background_default, backgroundView)
    }
}