package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_character_create.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.viewById
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class CreateCharacterFragment: BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    private val characterRecyclerView by viewById<RecyclerView>(R.id.rv_characters)
    private val characterAdapter: ChooseCharacterAdapter by lazy { ChooseCharacterAdapter() }
    override val layoutRes= R.layout.fragment_character_create

    companion object {
        fun newInstance() = CreateCharacterFragment()
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
        iniViewModel()
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener {backClicked()}
        initRecycler()
    }

    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationlScreen())
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
