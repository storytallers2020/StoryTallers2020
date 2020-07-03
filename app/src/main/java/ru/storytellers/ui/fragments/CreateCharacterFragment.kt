package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_character_create.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.viewById
import ru.storytellers.viewmodels.CreateCharacterViewModel

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
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun init() {
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener {backClicked()}
        initRecycler()
    }

    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationlScreen())
    }

    private fun initRecycler(){
       val staggeredGridLayoutManager = StaggeredGridLayoutManager(
           2,
           LinearLayoutManager.HORIZONTAL)
           .apply {reverseLayout=true}
        rv_characters.layoutManager=staggeredGridLayoutManager
        rv_characters.adapter=characterAdapter
    }
}
