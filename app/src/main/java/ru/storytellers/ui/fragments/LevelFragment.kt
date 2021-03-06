package ru.storytellers.ui.fragments

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.ResourceHelper
import ru.storytellers.viewmodels.LevelViewModel

class LevelFragment : BaseFragment<DataModel>() {

    override val model: LevelViewModel by inject()
    override val layoutRes = R.layout.fragment_level
    private val resourceHelper: ResourceHelper by lazy {
        ResourceHelper(this.context)
    }

    private val seekBarListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.let {
                model.setLevelGame(it.progress)
            }
        }
    }

    override fun init() {
        setClickListeners()
        initSeekBar()
    }

    private fun setClickListeners() {
        setBackButtonClickListener()
        btn_next.setOnClickListener { toCreateCharacterScreen() }
        btn_help.setOnClickListener { toRulesScreen() }
        setEasyButtonClickListener()
        setMediumButtonClickListener()
        setHardButtonClickListener()
    }

    private fun setBackButtonClickListener() {
        back_button_level.setOnClickListener {
            model.onBackClicked(this.javaClass.name)
            router.backTo(Screens.StartScreen())
        }
    }

    private fun toCreateCharacterScreen() {
        model.onNextScreen()
        if (model.isPlayerListNotEmpty()) router.navigateTo(Screens.TeamCharacterScreen())
        else router.navigateTo(Screens.CharacterCreateScreen())
    }

    private fun toRulesScreen(){
        router.navigateTo(Screens.RulesGameScreen())
    }

    private fun initSeekBar() {
        seekBar_lvl.setOnSeekBarChangeListener(seekBarListener)
    }

    private fun setEasyButtonClickListener() {
        easy_button.setOnClickListener {
            model.setLevelGame(resourceHelper.LEVEL_GAME_EASY)
        }
    }

    private fun setMediumButtonClickListener() {
        medium_button.setOnClickListener {
            model.setLevelGame(resourceHelper.LEVEL_GAME_MEDIUM)
        }
    }

    private fun setHardButtonClickListener() {
        hard_button.setOnClickListener {
            model.setLevelGame(resourceHelper.LEVEL_GAME_HARD)
        }
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
        model.setLevelGame(model.getLevelGame()) //Инициализация состояния
    }

    override fun iniViewModel() {
        model.subscribeOnChangeLevel().observe(viewLifecycleOwner, Observer {
            updateLevelBar(it)
        })
    }

    private fun updateLevelBar(levelId: Int) {
        seekBar_lvl.progress = levelId
        description_level.text = resourceHelper.getLevelDescription(levelId)

        easy_button.setColorFilter(resourceHelper.getColorWhite())
        medium_button.setColorFilter(resourceHelper.getColorWhite())
        hard_button.setColorFilter(resourceHelper.getColorWhite())

        when (levelId) {
            resourceHelper.LEVEL_GAME_HARD -> {
                hard_button.setColorFilter(resourceHelper.getColorYellow())
            }
            resourceHelper.LEVEL_GAME_MEDIUM -> {
                medium_button.setColorFilter(resourceHelper.getColorYellow())
            }
            else -> {
                easy_button.setColorFilter(resourceHelper.getColorYellow())
            }
        }
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

}