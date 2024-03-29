package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_choosing_cover.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.CoverAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.fieldsToLogString
import ru.storytellers.viewmodels.SelectCoverViewModel
import timber.log.Timber

class SelectCoverFragment : BaseFragment<DataModel>() {
    override val model: SelectCoverViewModel by inject()
    override val layoutRes = R.layout.fragment_choosing_cover

    companion object {
        fun newInstance() = SelectCoverFragment()
    }

    private val onListItemClickListener = object : CoverAdapter.OnListItemClickListener {
        override fun onItemClick(cover: Cover) {
            model.setCoverStory(cover)
            model.coverStatistics(cover)
            Timber.d(cover.fieldsToLogString())
            router.navigateTo(Screens.TitleAndSaveStoryScreen())
        }
    }
    private val coverAdapter: CoverAdapter by lazy { CoverAdapter(onListItemClickListener) }

    override fun init() {
        back_button_character.setOnClickListener { backToGameEndScreen() }
        initRecycler()
    }

    override fun onStart() {
        super.onStart()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.apply {
            getAllCover()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
        }
    }

    private fun initRecycler() {
        rv_covers.adapter = coverAdapter
    }

    private fun handlerOnSuccessResult(viewModel: SelectCoverViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            setLocationAdapter(it)
        })
    }

    private fun handlerOnErrorResult(viewModel: SelectCoverViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, Observer {
            Timber.e(it.error)
        })
    }

    private fun setLocationAdapter(it: DataModel.Success<Cover>) {
        coverAdapter.setData(it.data)
    }

    private fun backToGameEndScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.GameEndScreen())
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }
}