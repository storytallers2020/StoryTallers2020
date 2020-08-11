package ru.storytellers.ui.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_create.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.sentence_input_layout.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Location
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.CharacterCreateAdapter
import ru.storytellers.ui.adapters.LocationAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.utils.fieldsToLogString
import ru.storytellers.viewmodels.CharacterCreateViewModel
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber


class CharacterCreateFragment: BaseFragment<DataModel>() {
    override val model: CharacterCreateViewModel by inject()
    private var inputMethodManager: Any?= null
    override val layoutRes= R.layout.fragment_character_create
    private val onItemClickListener = { character: Character ->
        model.setCharacterOfPlayer(character)
    }
    private val characterAdapter: CharacterCreateAdapter by lazy { CharacterCreateAdapter(onItemClickListener) }
    companion object {
        fun newInstance() = CharacterCreateFragment()
    }
    private  val textWatcher= object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length<2) { makeInVisibleBtnNext()}
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(text: Editable) {
            if (text.toString().length>2) {
                makeVisibleBtnNext()
                model.setNamePlayer(text.toString())
            }
        }
    }

    override fun init() {
        rv_characters.adapter=characterAdapter
        enter_name_field_et.addTextChangedListener(textWatcher)
        iniViewModel()
        btn_next.setOnClickListener { handlerBtnNext() }
    }

    override fun iniViewModel() {
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        screen_header.post{ View.FOCUS_DOWN }
    }

    override fun onStart() {
        model.getAllCharacters()
        super.onStart()
    }

    override fun onResume() {
        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })

        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.let {
                setDataCharacterAdapter(it)
            }
        })
        super.onResume()
    }
    private fun setDataCharacterAdapter(it: DataModel.Success<Character>) {
        if (it.data!!.isNotEmpty()) {
            characterAdapter.setData(it.data)
        }
    }

    private fun hideKeyboard(){
        (inputMethodManager as? InputMethodManager)?.
        hideSoftInputFromWindow(enter_name_field_et.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun makeInVisibleBtnNext(){
        btn_next.visibility= View.GONE
    }
    private fun makeVisibleBtnNext(){
        btn_next.visibility= View.VISIBLE
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
    private fun handlerBtnNext(){
        model.run{
           addPlayer(createPlayer())
        }
        //router.navigateTo()
    }
}