package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.LocationAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.fieldsToLogString
import ru.storytellers.viewmodels.LocationViewModel
import timber.log.Timber

class LocationFragment : BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override val model: LocationViewModel by inject()

    private val onListItemClickListener = { location: Location ->
        with(location) {
            model.setLocationGame(this)
            model.onLocationChoiceStatistic(this)
        }

        Timber.d(location.fieldsToLogString())
        router.navigateTo(Screens.GameStartScreen())
    }
    private val locationAdapter: LocationAdapter by lazy { LocationAdapter(onListItemClickListener) }

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {
        rv_covers.adapter = locationAdapter
        back_from_location.setOnClickListener {
            backToTeamScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.apply {
            getAllLocations()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
        }
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

    private fun handlerOnSuccessResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            setLocationAdapter(it)
        })
    }

    private fun handlerOnErrorResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })
    }

    private fun setLocationAdapter(it: DataModel.Success<Location>) {
        locationAdapter.setData(it.data)
    }

    private fun backToTeamScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.TeamCharacterScreen())
    }

}