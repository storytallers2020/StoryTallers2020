package ru.storytallers.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.scope.currentScope
import ru.storytallers.R
import ru.storytallers.navigation.Screens
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.LevelViewModel
import ru.storytallers.model.DataModel

class LevelFragment: BaseFragment<DataModel>() {
    override lateinit var model: LevelViewModel
    override val layoutRes= R.layout.fragment_level
    private var levelGame: Int=1 //по умолчанию установлен легкий уровень
    private var backBtn: ImageView?=null
    private var easyBtn: TextView?=null
    private var mediumBtn: TextView?=null
    private var hardBtn: TextView?=null
    private var descriptionLvl: TextView?=null
    private var seekBar: AppCompatSeekBar?=null
    private var nextScrnBtn: MaterialButton?=null
    private val STATE_LVL_KEY="stateLevel"
    private val seekBarListener = object: OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val a =5
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            val a =5
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiComponents()
        setBtnClickListener()
        setSeekBarListener()
    }

    private fun setBtnClickListener() {
        backBtn?.setOnClickListener { backClicked() }
        easyBtn?.setOnClickListener {
            levelGame = 1
            seekBar?.progress=1
            setTextColorChoiceLvl()
            Toast.makeText(context,  "Выбран уровень Easy",
                Toast.LENGTH_LONG).show()
        }

        mediumBtn?.setOnClickListener {
            levelGame = 2
            seekBar?.progress=15
            setTextColorChoiceLvl()
            Toast.makeText(context,  "Выбран уровень Medium",
                Toast.LENGTH_LONG).show()
        }

        hardBtn?.setOnClickListener {
            levelGame = 3
            seekBar?.progress=29
            setTextColorChoiceLvl()
            Toast.makeText(context,  "Выбран уровень Hard",
                Toast.LENGTH_LONG).show()
        }

        nextScrnBtn?.setOnClickListener { toCreateCharacterScrn() }

    }

    private fun initUiComponents() {
        easyBtn = easy_button
        mediumBtn = medium_button
        hardBtn = hard_button
        backBtn = back_button_level
        descriptionLvl = description_level
        nextScrnBtn=next_button
        seekBar=seekBar_lvl
        seekBar?.max=30
    }

    private fun setLevel(progress: Int?){
        when(progress)
        {
            in 21..30 -> {
                levelGame=3 //3 - level Hard
                setTextColorChoiceLvl()
                Toast.makeText(context,  "Выбран уровень Hard",
                    Toast.LENGTH_LONG).show()
            }
            in 11..20 -> {
                levelGame=2 //2 - level Medium
                setTextColorChoiceLvl()
                Toast.makeText(context,  "Выбран уровень Medium",
                    Toast.LENGTH_LONG).show()
            }
            else -> {
                levelGame=1 //1 - level Easy
                setTextColorChoiceLvl()
                Toast.makeText(context,  "Выбран уровень Easy",
                    Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun setTextColorChoiceLvl(){
        val colorYellow=context?.resources?.getColor(R.color.yellow)
        val colorWhite=context?.resources?.getColor(R.color.white)
        when(levelGame){
             3 -> {
                 descriptionLvl?.text=context?.resources?.getString(R.string.hard_description)
                 colorYellow?.let {hardBtn?.setTextColor(it)}
                 colorWhite?.let {
                     mediumBtn?.setTextColor(it)
                     easyBtn?.setTextColor(it)
                 }
             }
            2-> {
                descriptionLvl?.text=context?.resources?.getString(R.string.medium_description)
                colorYellow?.let {mediumBtn?.setTextColor(it)}
                colorWhite?.let {
                    hardBtn?.setTextColor(it)
                    easyBtn?.setTextColor(it)
                }
            }
            else -> {
                descriptionLvl?.text=context?.resources?.getString(R.string.easy_description)
                colorYellow?.let {easyBtn?.setTextColor(it)}
                colorWhite?.let {
                    hardBtn?.setTextColor(it)
                    mediumBtn?.setTextColor(it)
                }
            }
        }
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