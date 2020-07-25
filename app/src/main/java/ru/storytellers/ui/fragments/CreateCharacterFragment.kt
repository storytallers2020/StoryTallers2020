package ru.storytellers.ui.fragments

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_character_create_v3.back_button_character
import kotlinx.android.synthetic.main.fragment_character_create_v3.btn_next
import kotlinx.android.synthetic.main.fragment_character_create_v3.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.adapters.PlayerAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class CreateCharacterFragment(): BaseFragment<DataModel>() {
    override val model: CreateCharacterViewModel by inject()
    private lateinit var characterAdapter: ChooseCharacterAdapter
    private lateinit var playerAdapter: PlayerAdapter
    private val playerCreator: PlayerCreator by inject()
    override val layoutRes= R.layout.fragment_character_create_v3
    private var imm: Any?= null
    companion object {
        fun newInstance() = CreateCharacterFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
        model.run {
            characterAdapter= ChooseCharacterAdapter(this,playerCreator)
            playerAdapter= PlayerAdapter(this)
            getAllCharacters()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
            handlerPlayerList(this)
            handlerFlagActive(this)
        }
    }
    private fun handlerFlagActive(viewModel:CreateCharacterViewModel) {
        viewModel.subscribeOnFlagActive().observe(viewLifecycleOwner, Observer {
            if (it){
                makeEditexiActive(enter_name_field_et)
                makeInvisible()
            model.setFlagActive(false)}
        })
    }

    private fun handlerOnErrorResult(viewModel:CreateCharacterViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })
    }

    private fun handlerOnSuccessResult(viewModel:CreateCharacterViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.let {
                setDataCharacterAdapter(it)
            }
        })
    }

    private fun setDataCharacterAdapter(it: DataModel.Success<Character>) {
        if (it.data!!.isNotEmpty()) {
            characterAdapter.setData(it.data)
        }
    }

    private fun handlerPlayerList(viewModel:CreateCharacterViewModel) {
        viewModel.subscribeOnPlayers().observe(
            viewLifecycleOwner,
            Observer {
                setPlayersToPlayerAdapter(it)
            })
    }

    private fun setPlayersToPlayerAdapter(it: List<Player>) {
        playerAdapter.setPlayersListData(it)
    }

    override fun init() {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        screen_header.post{ View.FOCUS_DOWN }
        iniViewModel()
        setOnEditorActionListener(enter_name_field_et)
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener {backClicked()}
        initRecyclers()
    }

    private fun setOnEditorActionListener(editTextView: TextInputEditText){
        enter_name_et_layout1.isErrorEnabled = false
        editTextView.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                val name=editTextView.text.toString()
                if(name!="") {
                    playerCreator.setNamePlayer(name)
                    editTextView.setText("")
                    makeEditexiInactive(editTextView)
                    makeVisible()
                    hideKeyBoard(editTextView)
                    return@setOnEditorActionListener true
                } else enter_name_et_layout1.error="Имя не может быть пустым"
            }
            return@setOnEditorActionListener false
        }
    }

    private fun makeEditexiInactive(editTextView: TextInputEditText) {
        editTextView.isEnabled = false
        enter_name_et_layout1.alpha = 0.3f
    }
    fun makeEditexiActive(editTextView: TextInputEditText) {
        editTextView.isEnabled = true
        enter_name_et_layout1.alpha = 1f
    }

    private fun hideKeyBoard(view: TextInputEditText){
        (imm as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }


    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationScreen())
    }

    private fun initRecyclers(){
        rv_characters.apply {
            adapter=characterAdapter
            makeInvisible()
        }
        player_list_rv.adapter=playerAdapter
    }

    private fun makeInvisible() {
        container_rv_chars.visibility = View.GONE
    }
    private fun makeVisible() {
        container_rv_chars.visibility = View.VISIBLE
    }

}
