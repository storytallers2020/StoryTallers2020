package ru.storytellers.ui.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_character_create.*
import kotlinx.android.synthetic.main.sentence_input_layout.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.CharacterCreateAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.CharacterCreateViewModel
import timber.log.Timber

class CharacterCreateFragment : BaseFragment<DataModel>() {
    override val model: CharacterCreateViewModel by inject()
    private var inputMethodManager: Any? = null
    override val layoutRes = R.layout.fragment_character_create
    private var isCharacterSelected = false
    private var isNameEntered = false
    private val characterAdapter: CharacterCreateAdapter by lazy {
        CharacterCreateAdapter(
            onItemClickListener
        )
    }

    private val onItemClickListener = { character: Character, position: Int ->
        model.setCharacterOfPlayer(character)
        isCharacterSelected = true
        statusCheck()
        characterAdapter.selectedPosition = position
        characterAdapter.notifyDataSetChanged()
        enter_name_field_et.isFocusable=false
    }

    companion object {
        fun newInstance() = CharacterCreateFragment()
    }

    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length < 3) {
                isNameEntered = false
                statusCheck()
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(text: Editable) {
            if (text.toString().length > 2) {
                isNameEntered = true
                statusCheck()
                model.setNamePlayer(text.toString())
            }
        }
    }

    override fun init() {
        rv_characters.adapter = characterAdapter
        with( enter_name_field_et){
            /*
            setOnTouchListener { v, event ->
                v.isFocusable=true
            false}
             */
            addTextChangedListener(textWatcher)
            setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus) hideKeyboard()
            }
        }
      //  enter_name_field_et.addTextChangedListener(textWatcher)
        iniViewModel()
        back_button_character.setOnClickListener { backToLevelScreen() }
        btn_next.setOnClickListener { handlerBtnNext() }
    }

    override fun iniViewModel() {
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        //screen_header.post { View.FOCUS_DOWN }
    }

    override fun onStart() {
        super.onStart()
        val editable = Editable.Factory.getInstance().newEditable("")
        enter_name_field_et.text = editable
        model.getAllCharacters()
    }

    override fun onResume() {
        super.onResume()
        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })

        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            setDataCharacterAdapter(it)
        })
    }

    private fun statusCheck() {
        btn_next.isEnabled = isCharacterSelected && isNameEntered
    }

    private fun setDataCharacterAdapter(it: DataModel.Success<Character>) {
        if (it.data!!.isNotEmpty()) {
            characterAdapter.setData(it.data)
        }
    }
    private fun hideKeyboard() {
        (inputMethodManager as? InputMethodManager)?.hideSoftInputFromWindow(
            enter_name_field_et.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        enter_name_field_et.isFocusable=true

    }

    private fun backToLevelScreen(){
        router.backTo(Screens.LevelScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun handlerBtnNext() {
        isCharacterSelected = false
        isNameEntered = false
        model.run {
            getPlayer()?.let { player ->
                    addPlayer(player)
                    router.navigateTo(Screens.TeamCharacterScreen())
            }
        }
    }
}