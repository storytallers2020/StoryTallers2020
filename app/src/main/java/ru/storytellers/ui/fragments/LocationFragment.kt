package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
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

    private val onListItemClickListener = object : LocationAdapter.OnListItemClickListener {
        override fun onItemClick(location: Location) {
            // Сохранение во временное хранилище
            //StoryTallerApp.instance.gameStorage.location = location
            Timber.d(location.fieldsToLogString())
            // Тут должен быть переход. По хорошему тоже не прямой, а через модель.
            // Типа model.OnLocationSelected(location)
            // А в model будет onReadyToGotoNextScreen на которую тут подписаться и уже переходить.
            // Как-то так, но пока не уверен, нужно вспомнить, как мы это делали на уроках.
            // Поэтому в первом приближении можно тут все сделать.
            router.navigateTo(Screens.GameScreen())
        }
    }

    // Тут немного по другому. Как на курсе А3
    private val locationAdapter: LocationAdapter by lazy { LocationAdapter(onListItemClickListener) }

    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {

        iniViewModel()

        btn_next.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
        back_from_location.setOnClickListener { backClicked() }


        val recyclerView: RecyclerView = view?.findViewById(R.id.rv_covers)!!
        //val layoutManager =
        //    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Не нужно. Кей это сделала в разметке
        //recyclerView.layoutManager = layoutManager

        // Ошибка была тут! Ты же уже создал адаптер в iniViewModel и передал его в ViewModel.
        // Зачем ты тут еще раз создаешь?
        //recyclerView.adapter = LocationAdapter()
        recyclerView.adapter = locationAdapter
    }

    override fun iniViewModel() {
        //model.run {
        //Run отличается от apply тем, что может возвращать результать. Нам это не нужно.
        model.apply {
            // Переделал на lazy см. выше
            //locationAdapter = LocationAdapter(onListItemClickListener)
            getAllLocations()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
        }
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun handlerOnSuccessResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            // Знак вопроса забыл
            it.data?.let { locationList ->
                setLocationAdapter(locationList)
            }
        })
    }

    private fun handlerOnErrorResult(viewModel: LocationViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })
    }

    // it плохое имя для входной переменной. Да и DataModel тут уже не нужен.
    private fun setLocationAdapter(locationList: List<Location>) {
        // Лишняя проверка. Нет данных, значит пустые установим.
        //if (it.data!!.isNotEmpty()) {
        locationAdapter.setData(locationList)
        //}
    }

}

