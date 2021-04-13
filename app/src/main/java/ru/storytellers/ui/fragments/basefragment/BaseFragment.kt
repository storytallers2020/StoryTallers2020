package ru.storytellers.ui.fragments.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.image.IImageLoader
import ru.storytellers.ui.BackButtonListener
import ru.storytellers.utils.loadImage
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


abstract class BaseFragment<T : DataModel> : Fragment(), BackButtonListener {
    abstract val model: BaseViewModel<T>
    abstract val layoutRes: Int
    protected val router: Router by inject()
    protected val imageLoader: IImageLoader by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val backgroundView: View by lazy { requireActivity().findViewById(R.id.main_background) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setDefaultBackground()
    }

    protected fun setBackground(url: String) {
        imageLoader.loadBackground(url, backgroundView)
    }

    private fun setDefaultBackground() {
        loadImage(R.drawable.ic_background_default, backgroundView)
    }

    protected fun showProgressBar(progressBar: ProgressBar, recyclerView: RecyclerView) {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    protected fun hideProgressBar(progressBar: ProgressBar, recyclerView: RecyclerView) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    protected abstract fun init()

    protected abstract fun initViewModel()
}