package ru.storytellers.ui.fragments

import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.ResourceProviderLevelFragment
import ru.storytellers.viewmodels.LevelViewModel
import kotlin.properties.Delegates

class LevelFragment : BaseFragment<DataModel>() {
    override val model: LevelViewModel by inject()
    override val layoutRes = R.layout.fragment_level
    private val resourceProvider: ResourceProviderLevelFragment by lazy {
        ResourceProviderLevelFragment(
            this.context
        )
    }
    private val MAX_VALUE_RANGE_SEEK_BAR = 2
    private val LEVEL_GAME_EASY = 0
    private val LEVEL_GAME_MEDIUM = 1
    private val LEVEL_GAME_HARD = 2
    private var  levelGame=0
    private var backBtn: ImageView? = null
    private var easyBtn: TextView? = null
    private var mediumBtn: TextView? = null
    private var hardBtn: TextView? = null
    private var descriptionLvl: TextView? = null
    private var seekBar: AppCompatSeekBar? = null
    private var nextScrnBtn: MaterialButton? = null
    private val seekBarListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            val progressLocal = seekBar?.progress
            setLevel(progressLocal)
        }
    }

    companion object {
        fun newInstance() = LevelFragment()
    }


    override fun init() {
        initUiComponents()
        setClickListeners()
        setSeekBarListener()
    }

    override fun iniViewModel() {
        model.subscribeOnLevelGame().observe(viewLifecycleOwner, Observer {
            levelGame = it
        })
    }

    override fun onStart() {
        super.onStart()
        model.getLevelGame()
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
        setSeekBarProgress()
        setTextColorChoiceLevel()
    }

    private fun initUiComponents() {
        easyBtn = easy_button
        mediumBtn = medium_button
        hardBtn = hard_button
        backBtn = back_button_level
        descriptionLvl = description_level
        nextScrnBtn = btn_next
        seekBar = seekBar_lvl
        seekBar?.max = MAX_VALUE_RANGE_SEEK_BAR


    }

    private fun setClickListeners() {
        setClickListenerBackButton()
        setClickListenerEasyBtn()
        setClickListenerMediumBtn()
        setClickListenerHardBtn()
        setClickListenerNextBtn()
    }

    private fun setSeekBarListener() {
        seekBar?.setOnSeekBarChangeListener(seekBarListener)
    }

    private fun setClickListenerNextBtn() {
        nextScrnBtn?.setOnClickListener {
            toCreateCharacterScrn()
        }
    }

    private fun setClickListenerHardBtn() {
        hardBtn?.setOnClickListener {
            model.setLevelGame(LEVEL_GAME_HARD)
            setSeekBarProgress()
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerMediumBtn() {
        mediumBtn?.setOnClickListener {
            model.setLevelGame(LEVEL_GAME_MEDIUM)
            setSeekBarProgress()
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerEasyBtn() {
        easyBtn?.setOnClickListener {
            model.setLevelGame(LEVEL_GAME_EASY)
            setSeekBarProgress()
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerBackButton() {
        backBtn?.setOnClickListener { router.backTo(Screens.StartScreen()) }
    }

    private fun setSeekBarProgress() {
        seekBar?.progress = levelGame
    }

    private fun setLevel(progress: Int?) {
        when (progress) {
            LEVEL_GAME_HARD -> {
                //2 - level Hard
                setLvlAndSetTextColor(LEVEL_GAME_HARD)
            }
            LEVEL_GAME_MEDIUM -> {
                //1 - level Medium
                setLvlAndSetTextColor(LEVEL_GAME_MEDIUM)
            }
            else -> {
                //0 - level Easy
                setLvlAndSetTextColor(LEVEL_GAME_EASY)
            }
        }
    }

    private fun setLvlAndSetTextColor(levelGame:Int) {
        model.setLevelGame(levelGame)
        setTextColorChoiceLevel()
    }

    private fun setTextColorChoiceLevel() {
        val colorYellow = resourceProvider.getColorYellow()
        val colorWhite = resourceProvider.getColorWhite()
        val descriptionHardLvl = resourceProvider.getTextDescriptionHardLvl()
        val descriptionMediumLvl = resourceProvider.getTextDescriptionMediumLvl()
        val descriptionEasyLvl = resourceProvider.getTextDescriptionEasyLvl()
        when (levelGame) {
            LEVEL_GAME_HARD -> {
                setDescriptionLevel(descriptionHardLvl)
                setTextColorLevelHard(colorWhite, colorYellow)
            }
            LEVEL_GAME_MEDIUM -> {
                setDescriptionLevel(descriptionMediumLvl)
                setTextColorLevelMedium(colorWhite, colorYellow)
            }
            else -> {
                setDescriptionLevel(descriptionEasyLvl)
                setTextColorLevelEasy(colorWhite, colorYellow)
            }
        }
    }

    private fun setTextColorLevelEasy(colorWhite: Int?, colorYellow: Int?) {
        colorWhite?.let {
            setTextColorHardBtn(it)
            setTextColorMediumBtn(it)
        }
        colorYellow?.let { setTextColorEasyBtn(it) }
    }

    private fun setTextColorLevelMedium(colorWhite: Int?, colorYellow: Int?) {
        colorWhite?.let {
            setTextColorHardBtn(it)
            setTextColorEasyBtn(it)
        }
        colorYellow?.let { setTextColorMediumBtn(it) }
    }

    private fun setTextColorLevelHard(colorWhite: Int?, colorYellow: Int?) {
        colorWhite?.let {
            setTextColorMediumBtn(it)
            setTextColorEasyBtn(it)
        }
        colorYellow?.let { setTextColorHardBtn(it) }
    }

    private fun setTextColorHardBtn(color: Int?) {
        color?.let { hardBtn?.setTextColor(it) }
    }

    private fun setTextColorMediumBtn(color: Int?) {
        color?.let { mediumBtn?.setTextColor(it) }
    }

    private fun setTextColorEasyBtn(color: Int?) {
        color?.let { easyBtn?.setTextColor(it) }
    }


    private fun setDescriptionLevel(description: String?) {
        description?.let { descriptionLvl?.text = it }
    }

    private fun toCreateCharacterScrn() {
       if( model.listPlayerIsNotEmpty()) router.navigateTo(Screens.TeamCharacterScreen())
       else router.navigateTo(Screens.CharacterCreateScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}
