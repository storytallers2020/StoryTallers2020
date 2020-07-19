package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.viewmodels.LocationViewModel

class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override lateinit var model: LocationViewModel

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {
        iniViewModel()
        btn_next.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
        back_from_location.setOnClickListener {backClicked()}

        val recyclerView: RecyclerView = view?.findViewById(R.id.rv_locations)!!
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = layoutManager
        val data = Data()
        val myAdapter = LocationAdapter(data.getDataList())
        recyclerView.adapter = myAdapter
    }

    override fun iniViewModel() {
        val viewModel: LocationViewModel by inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    inner class Data {
        val list: List<String> = listOf(getString(R.string.location_1), getString(R.string.loation_2))

        fun getDataList(): List<String>? {
            return list
        }
    }
}