package ru.storytellers.ui.fragments

import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.LevelViewModel
import ru.storytellers.model.DataModel

class LevelFragment: BaseFragment<DataModel>() {
    override lateinit var model: LevelViewModel
    override val layoutRes= R.layout.fragment_level
    private val MAX_VALUE_RANGE_SEEK_BAR=2
    private val MIN_VALUE_PROGRESS_SEEK_BAR=0
    private val MEDIUM_VALUE_PROGRESS_SEEK_BAR=1
    private val MAX_VALUE_PROGRESS_SEEK_BAR=2
    private val LEVEL_GAME_EASY=1
    private val LEVEL_GAME_MEDIUM=2
    private val LEVEL_GAME_HARD=3
    private var levelGame=LEVEL_GAME_EASY //по умолчанию установлен легкий уровень
    private var backBtn: ImageView?=null
    private var easyBtn: TextView?=null
    private var mediumBtn: TextView?=null
    private var hardBtn: TextView?=null
    private var descriptionLvl: TextView?=null
    private var seekBar: AppCompatSeekBar?=null
    private var nextScrnBtn: MaterialButton?=null
    private val seekBarListener = object: OnSeekBarChangeListener{
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

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun init() {
        initUiComponents()
        setClickListeners()
        setSeekBarListener()
    }

    private fun setClickListeners() {
        setClickListenerBackButton()
        setClickListenerEasyBtn()
        setClickListenerMediumBtn()
        setClickListenerHardBtn()
        setClickListenerNextBtn()
    }

    private fun setClickListenerNextBtn() {
        nextScrnBtn?.setOnClickListener {
            model.setLevelGame(levelGame)
            toCreateCharacterScrn() }
    }

    private fun setClickListenerHardBtn() {
        hardBtn?.setOnClickListener {
            levelGame = LEVEL_GAME_HARD
            seekBar?.progress = MAX_VALUE_PROGRESS_SEEK_BAR
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerMediumBtn() {
        mediumBtn?.setOnClickListener {
            levelGame = LEVEL_GAME_MEDIUM
            seekBar?.progress = MEDIUM_VALUE_PROGRESS_SEEK_BAR
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerEasyBtn() {
        easyBtn?.setOnClickListener {
            levelGame = LEVEL_GAME_EASY
            seekBar?.progress = MIN_VALUE_PROGRESS_SEEK_BAR
            setTextColorChoiceLevel()
        }
    }

    private fun setClickListenerBackButton() {
        backBtn?.setOnClickListener { backClicked() }
    }

    private fun initUiComponents() {
        easyBtn = easy_button
        mediumBtn = medium_button
        hardBtn = hard_button
        backBtn = back_button_level
        descriptionLvl = description_level
        nextScrnBtn=btn_next
        seekBar=seekBar_lvl
        seekBar?.max=MAX_VALUE_RANGE_SEEK_BAR
    }

    private fun setLevel(progress: Int?){
        when(progress)
        {
            MAX_VALUE_PROGRESS_SEEK_BAR -> {
                levelGame=LEVEL_GAME_HARD //3 - level Hard
                setTextColorChoiceLevel()
            }
            MEDIUM_VALUE_PROGRESS_SEEK_BAR -> {
                levelGame=LEVEL_GAME_MEDIUM //2 - level Medium
                setTextColorChoiceLevel()
            }
            else -> {
                levelGame=LEVEL_GAME_EASY //1 - level Easy
                setTextColorChoiceLevel()
            }
        }
    }

    private fun setTextColorChoiceLevel(){
        val colorYellow= getColorYellow()
        val colorWhite= getColorWhite()
        when(levelGame){
            LEVEL_GAME_HARD -> {
                setDescriptionLevel(getTextDescriptionHardLvl())
                setTextColorLevelHard(colorWhite,colorYellow)
             }
            LEVEL_GAME_MEDIUM-> {
                setDescriptionLevel(getTextDescriptionMediumLvl())
                setTextColorLevelMedium(colorWhite,colorYellow)
            }
            else -> {
                setDescriptionLevel(getTextDescriptionEasyLvl())
                setTextColorLevelEasy(colorWhite,colorYellow)
            }
        }
    }

    private fun getColorWhite() =
        context?.let { ContextCompat.getColor(it, R.color.white) }


    private fun getColorYellow() =
        context?.let { ContextCompat.getColor(it, R.color.yellow) }


    private fun getTextDescriptionMediumLvl() =
        context?.resources?.getString(R.string.medium_description)

    private fun getTextDescriptionEasyLvl() =
        context?.resources?.getString(R.string.easy_description)

    private fun getTextDescriptionHardLvl() = context?.resources?.getString(R.string.hard_description)

    private fun setTextColorLevelEasy(colorWhite:Int?,colorYellow:Int?) {
        colorWhite?.let{
            setTextColorHardBtn(it)
            setTextColorMediumBtn(it)
        }
        colorYellow?.let {setTextColorEasyBtn(it)}
    }
    private fun setTextColorLevelMedium(colorWhite:Int?,colorYellow:Int?) {
        colorWhite?.let{
            setTextColorHardBtn(it)
            setTextColorEasyBtn(it)
        }
        colorYellow?.let {setTextColorMediumBtn(it)}
    }
    private fun setTextColorLevelHard(colorWhite:Int?,colorYellow:Int?) {
        colorWhite?.let{
            setTextColorMediumBtn(it)
            setTextColorEasyBtn(it)
        }
        colorYellow?.let {setTextColorHardBtn(it)}
    }
    private fun setTextColorHardBtn(color:Int?){
        color?.let { hardBtn?.setTextColor(it) }
    }
    private fun setTextColorMediumBtn(color:Int?){
        color?.let { mediumBtn?.setTextColor(it) }
    }
    private fun setTextColorEasyBtn(color:Int?){
        color?.let { easyBtn?.setTextColor(it) }
    }
    private fun setDescriptionLevel(description:String?){
        description?.let {descriptionLvl?.text=it}
    }

    private fun setSeekBarListener(){
        seekBar?.setOnSeekBarChangeListener(seekBarListener)
    }
    private fun toCreateCharacterScrn(){
        router.navigateTo(Screens.CreateCharacterScreen())
    }

    override fun iniViewModel() {
        val viewModel: LevelViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }
}