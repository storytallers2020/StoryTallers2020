package ru.storytellers.ui.fragments.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.storytellers.model.DataModel
import ru.storytellers.ui.BackButtonListener
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


abstract class BaseFragment<T : DataModel> : Fragment(), BackButtonListener {
    abstract val model: BaseViewModel<T>
    abstract val layoutRes: Int

    protected val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    protected abstract fun init()

    protected abstract fun initViewModel()
}