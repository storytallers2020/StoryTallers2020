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
    private val MAX_VALUE_RANGE_SEEK_BAR = 2

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
        back_button_level.setOnClickListener { router.backTo(Screens.StartScreen()) }
        btn_next.setOnClickListener { toCreateCharacterScreen() }

        setEasyButtonClickListener()
        setMediumClickListenerMediumBtn()
        setClickListenerHardBtn()
    }

    private fun toCreateCharacterScreen() {
        if (model.isPlayerListNotEmpty()) router.navigateTo(Screens.TeamCharacterScreen())
        else router.navigateTo(Screens.CharacterCreateScreen())
    }

    private fun initSeekBar() {
        seekBar_lvl.max = MAX_VALUE_RANGE_SEEK_BAR
        seekBar_lvl.setOnSeekBarChangeListener(seekBarListener)
    }

    private fun setEasyButtonClickListener() {
        easy_button.setOnClickListener {
            model.setLevelGame(resourceHelper.LEVEL_GAME_EASY)
        }
    }

    private fun setMediumClickListenerMediumBtn() {
        medium_button.setOnClickListener {
            model.setLevelGame(resourceHelper.LEVEL_GAME_MEDIUM)
        }
    }

    private fun setClickListenerHardBtn() {
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
        easy_button.setTextColor(resourceHelper.getColorWhite())
        medium_button.setTextColor(resourceHelper.getColorWhite())
        hard_button.setTextColor(resourceHelper.getColorWhite())

        when (levelId) {
            resourceHelper.LEVEL_GAME_HARD -> {
                hard_button.setTextColor(resourceHelper.getColorYellow())
            }
            resourceHelper.LEVEL_GAME_MEDIUM -> {
                medium_button.setTextColor(resourceHelper.getColorYellow())
            }
            else -> {
                easy_button.setTextColor(resourceHelper.getColorYellow())
            }
        }
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

}