package ru.storytellers.ui.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_create.*
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
    private val characterAdapter: CharacterCreateAdapter by lazy { CharacterCreateAdapter(onItemClickListener) }

    private val onItemClickListener = { character: Character, position: Int ->
        model.setCharacterOfPlayer(character)
        characterAdapter.selectedPosition = position
        characterAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = CharacterCreateFragment()
    }

    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length < 3) {
                btn_next.isEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(text: Editable) {
            if (text.toString().length > 2) {
                btn_next.isEnabled = true
                model.setNamePlayer(text.toString())
            }
        }
    }

    override fun init() {
        rv_characters.adapter = characterAdapter
        enter_name_field_et.addTextChangedListener(textWatcher)
        iniViewModel()
        btn_next.setOnClickListener { handlerBtnNext() }
    }

    override fun iniViewModel() {
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        screen_header.post { View.FOCUS_DOWN }
    }

    override fun onStart() {
        val editable = Editable.Factory.getInstance().newEditable("")
        enter_name_field_et.text = editable
        model.getAllCharacters()
        super.onStart()
    }

    override fun onResume() {
        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })

        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            setDataCharacterAdapter(it)
        })
        super.onResume()
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
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun handlerBtnNext() {
        model.run {
            getPlayer()?.let {
                addPlayer(it)
                router.navigateTo(Screens.TeamCharacterScreen())
            } ?: context?.let { toastShowLong(it, "Персонаж не выбран!") }
        }

    }
}