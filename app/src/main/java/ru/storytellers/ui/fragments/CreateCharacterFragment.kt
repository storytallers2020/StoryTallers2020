package ru.storytellers.ui.fragments

import android.view.inputmethod.EditorInfo
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
import ru.storytellers.utils.viewById
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class CreateCharacterFragment(private val levelGame:Int): BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    private val characterRecyclerView by viewById<RecyclerView>(R.id.rv_characters)
    private val playerRecyclerView by viewById<RecyclerView>(R.id.player_list_rv)
    private val characterAdapter: ChooseCharacterAdapter by lazy { ChooseCharacterAdapter() }
    private val playerAdapter: PlayerAdapter by lazy { PlayerAdapter() }
    override val layoutRes= R.layout.fragment_character_create_v3

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
            getAllCharacters()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
            handlerNamePlayerList(this)
        }
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
    private fun handlerNamePlayerList(viewModel:CreateCharacterViewModel) {
        viewModel.subscribePlayersLiveData().observe(viewLifecycleOwner, Observer {
            it.let {
                if (it.isNotEmpty()) {
                    playerAdapter.setDataToPlayerAdapter(it)
                }
            }
        })
    }

    override fun init() {
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
                model.addNamePlayerToList(editTextView.text.toString())
                editTextView.setText("")
               // editTextView.isEnabled=false

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }


    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationScreen())
    }

    private fun initRecyclers(){
       val gridLayoutManager = GridLayoutManager(
           context,
           2,
           LinearLayoutManager.HORIZONTAL,
           false
           )
        characterRecyclerView.apply {
            layoutManager=gridLayoutManager
            adapter=characterAdapter
        }
        val linearLayoutManager=LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        playerRecyclerView.apply {
            layoutManager=linearLayoutManager
            adapter=playerAdapter
        }
    }
}
