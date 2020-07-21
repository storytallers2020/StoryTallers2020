package ru.storytellers.ui.fragments

import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.ui.adapters.LocationAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.LocationViewModel

class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override lateinit var model: LocationViewModel

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {
        iniViewModel()
//        btn_next.setOnClickListener {                     кнопки на экране больше нет, переход в игру по клику на элемент ресайклера
//            router.navigateTo(Screens.GameScreen())
//        }
//        back_from_location.setOnClickListener {backClicked()}


        val recyclerView: RecyclerView = view?.findViewById(R.id.rv_covers)!!
//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)   УДАЛИТЬ
//        recyclerView.layoutManager = layoutManager                                                УДАЛИТЬ
        val data = Data()
        val myAdapter =
            LocationAdapter(data.getDataList(),router)
        recyclerView.adapter = myAdapter
    }

    override fun iniViewModel() {
        val viewModel: LocationViewModel by inject()
        model = viewModel
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    inner class Data {
        val list: List<String> = listOf(getString(R.string.location_1), getString(R.string.location_2))

        fun getDataList(): List<String>? {
            return list
        }
    }
}