package ru.storytellers.ui.fragments

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
        rv_locations.adapter = locationAdapter
        back_from_location.setOnClickListener {
            backToTeamScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun initViewModel() {
        model.run {
            getAllLocations()
            handlerOnSuccessResult(this)
            handlerOnLoadingResult(this)
            handlerOnErrorResult(this)
        }
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }


    private fun handlerOnSuccessResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, {
            setLocationAdapter(it)
        })
    }

    private fun handlerOnLoadingResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnLoading().observe(viewLifecycleOwner, {
            when (it.progress ?: 0) {
                in 1..99 -> showProgressBar(progress_bar, rv_locations)
                else -> hideProgressBar(progress_bar, rv_locations)
            }
        })
    }

    private fun handlerOnErrorResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, {
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