package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.ChooseCharacterAdapter
import ru.storytellers.ui.adapters.LocationAdapter
import ru.storytellers.viewmodels.LocationViewModel
import timber.log.Timber

class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override val model: LocationViewModel by inject()
    private lateinit var locationAdapter: LocationAdapter

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {

        iniViewModel()

        btn_next.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
        back_from_location.setOnClickListener {backClicked()}


        val recyclerView: RecyclerView = view?.findViewById(R.id.rv_covers)!!
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = locationAdapter
    }

    override fun iniViewModel() {
        model.apply {
            locationAdapter = LocationAdapter()
            getAllLocations()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
        }
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun handlerOnSuccessResult(viewModel : LocationViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.let {
                setLocationAdapter(it)
            }
        })
    }

    private fun handlerOnErrorResult(viewModel : LocationViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })
    }

    private fun setLocationAdapter(it: DataModel.Success<Location>) {
        if (it.data!!.isNotEmpty()) {
            locationAdapter.setData(it.data)
        }
    }

}