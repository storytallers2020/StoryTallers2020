package ru.storytellers.ui.fragments

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_create.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.CharacterCreateAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.hideSoftKey
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.CharacterCreateViewModel
import timber.log.Timber

class CharacterCreateFragment : BaseFragment<DataModel>() {
    private var isCharacterSelected = false
    private var isNameEntered = false
    override val model: CharacterCreateViewModel by inject()
    private var inputMethodManager: Any? = null
    override val layoutRes = R.layout.fragment_character_create
    private val characterAdapter: CharacterCreateAdapter by lazy {
        CharacterCreateAdapter(
            onItemClickListener
        )
    }

    private val onItemClickListener = { character: Character, position: Int ->
        model.characterSelected(character)
        characterAdapter.selectedPosition = position
        characterAdapter.notifyDataSetChanged()
        hideSoftKey(enter_name_field_et)
    }

    private val focusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            hideSoftKey(v)
        }
    }

    companion object {
        fun newInstance() = CharacterCreateFragment()
    }

    override fun init() {
        rv_characters.adapter = characterAdapter
        initViewModel()
        back_button_character.setOnClickListener { backToLevelScreen() }
        btn_next.setOnClickListener { handlerBtnNext() }
    }

    override fun initViewModel() {
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        //screen_header.post { View.FOCUS_DOWN }
    }

    override fun onStart() {
        super.onStart()
        enter_name_field_et.apply {
            setText("")
            onFocusChangeListener = focusListener
        }
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
        model.subscribeOnCharacterSelected()
            .observe(
                viewLifecycleOwner,
                Observer { isCharacterSelected = it }
            )

        model.inputValid.subscribeOnInputIncorrect().observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {
                    setError(getString(R.string.err_short_name))
                }
                2 -> {
                    setError(getString(R.string.err_name_is_gaps))
                }
                else -> {
                    enter_name_et_layout1.error = null
                    isNameEntered=true
                }
            }
        })
    }


    private fun setError(nameError: String) {
        isNameEntered=false
        enter_name_et_layout1.error = nameError
    }

    private fun setDataCharacterAdapter(it: DataModel.Success<Character>) {
        if (it.data!!.isNotEmpty()) {
            characterAdapter.setData(it.data)
        }
    }

    private fun statusCheck() = isCharacterSelected && isNameEntered


    private fun backToLevelScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.LevelScreen())
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

    private fun handlerBtnNext() {
        model.transferNamePlayer(enter_name_field_et.text)
        if (statusCheck()) {
            model.isCharacterSelected = false
            isNameEntered = false
            router.navigateTo(Screens.TeamCharacterScreen())
        } else if (!isCharacterSelected) toastShowLong(
            context,
            getString(R.string.err_character_not_selected)
        )
    }
}