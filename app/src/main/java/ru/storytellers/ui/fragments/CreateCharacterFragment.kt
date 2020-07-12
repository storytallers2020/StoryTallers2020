package ru.storytellers.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_character_create.back_button_character
import kotlinx.android.synthetic.main.fragment_character_create.btn_next
import kotlinx.android.synthetic.main.fragment_character_create_v2.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.viewById
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class CreateCharacterFragment(private val levelGame:Int): BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    private val characterRecyclerView by viewById<RecyclerView>(R.id.rv_characters)
    private val characterAdapter: ChooseCharacterAdapter by lazy { ChooseCharacterAdapter() }
    override val layoutRes= R.layout.fragment_character_create_v2
    private var inputNameGamer: String?=null
    private var nameGamer1: String?=null
    private var nameGamer2: String?=null
    private var nameGamer3: String?=null
    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {
            inputNameGamer=s.toString()

        }
    }

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

    override fun init() {
        input_name_field1_et.tag="namePlayer1"
        input_name_field2_et.tag = "namePlayer2"
        input_name_field3_et.tag = "namePlayer3"
        setTextChangeListener(input_name_field1_et)
        setTextChangeListener(input_name_field2_et)
        setTextChangeListener(input_name_field3_et)
        iniViewModel()
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener {backClicked()}
        initRecycler()
    }

    private fun setTextChangeListener(inputNameField:TextInputEditText){
        inputNameField.setOnFocusChangeListener { view, hasFocus ->
           view.run { this as? TextInputEditText }
               ?.let { textInputEditText->
                   if (hasFocus) textInputEditText.addTextChangedListener(textWatcher)
                   else {
                       selectionOfEnteredValue(textInputEditText)
                           textInputEditText.removeTextChangedListener(textWatcher)}
               }
        }
    }

    private fun selectionOfEnteredValue(textInputEditText: TextInputEditText) {
        when (textInputEditText.tag) {
            "namePlayer1" -> {
                inputNameGamer?.let {
                    nameGamer1 = it
                }
            }
            "namePlayer2" -> {
                inputNameGamer?.let {
                    nameGamer2 = it
                }
            }
            else -> {
                inputNameGamer?.let {
                    nameGamer3 = it
                }
            }
        }
    }

    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationScreen())
    }

    private fun initRecycler(){
       val gridLayoutManager = GridLayoutManager(
           context,
           2,
           LinearLayoutManager.HORIZONTAL,
           true
           )
        characterRecyclerView.apply {
            layoutManager=gridLayoutManager
            adapter=characterAdapter
        }
    }
}
