package ru.storytellers.ui.fragments

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_character_create_v3.back_button_character
import kotlinx.android.synthetic.main.fragment_character_create_v3.btn_next
import kotlinx.android.synthetic.main.fragment_character_create_v3.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.adapters.PlayerAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.utils.viewById
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class CreateCharacterFragment(private val levelGame:Int): BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    private val characterRecyclerView by viewById<RecyclerView>(R.id.rv_characters)
    private val playerRecyclerView by viewById<RecyclerView>(R.id.player_list_rv)
    private lateinit var characterAdapter: ChooseCharacterAdapter
    private val playerAdapter: PlayerAdapter by lazy { PlayerAdapter() }
    private val playerCreator: PlayerCreator by lazy { PlayerCreator() }
    override val layoutRes= R.layout.fragment_character_create_v3
    private var imm: Any?= null


    companion object {
        fun newInstance(levelGame:Int) = CreateCharacterFragment(levelGame)

    }


    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
        val viewModel: CreateCharacterViewModel by currentScope.inject()
        model = viewModel
        model.run {
            characterAdapter=ChooseCharacterAdapter(this,playerCreator)
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
                characterRecyclerView.makeInvisible()
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
                if (it.data!!.isNotEmpty()) {
                    characterAdapter.setData(it.data)
                }
            }
        })
    }
    private fun handlerPlayerList(viewModel:CreateCharacterViewModel) {
        viewModel.run {
            subscribeOnPlayers().observe(viewLifecycleOwner, Observer {
                playerAdapter.setPlayersListData(it)
            })
        }

    }

    override fun init() {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        creation_header.post{ View.FOCUS_DOWN }
        iniViewModel()
        setOnEditorActionListener(enter_name_field_et)
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener {backClicked()}
        initRecyclers()
    }

    private fun setOnEditorActionListener(editTextView: TextInputEditText){
        editTextView.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                val name=editTextView.text.toString()
                if(name!="") {
                    playerCreator.setNamePlayer(name)
                    editTextView.setText("")
                    makeEditexiInactive(editTextView)
                    characterRecyclerView.makeVisible()
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
        characterRecyclerView.apply {
            layoutManager=GridLayoutManager(
                context,
                2,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter=characterAdapter
            makeInvisible()

        }
        playerRecyclerView.apply {
            layoutManager=LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter=playerAdapter
        }
    }

    private fun RecyclerView.makeInvisible() {
        visibility = View.GONE
        title_rv_characters.visibility= View.GONE
    }
    private fun RecyclerView.makeVisible() {
        visibility = View.VISIBLE
        title_rv_characters.visibility = View.VISIBLE
    }

}
