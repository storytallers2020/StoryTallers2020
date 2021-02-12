package ru.storytellers.ui.fragments

import kotlinx.android.synthetic.main.fragment_choosing_cover.*
import kotlinx.android.synthetic.main.fragment_choosing_cover.back_button_character
import kotlinx.android.synthetic.main.fragment_choosing_cover.progress_bar
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
        initViewModel()
    }

    override fun initViewModel() {
        model.apply {
            getAllCover()
            handlerOnSuccessResult(this)
            handlerOnErrorResult(this)
            handlerEnabledProgressBar(this)
        }
    }

    private fun initRecycler() {
        rv_covers.adapter = coverAdapter
    }

    private fun handlerOnSuccessResult(viewModel: SelectCoverViewModel) {
        viewModel.subscribeOnSuccess().observe(viewLifecycleOwner, {
            setLocationAdapter(it)
        })
    }

    private fun handlerEnabledProgressBar(viewModel: SelectCoverViewModel) {
        viewModel.subscribeOnProgressEnableLiveData()
            .observe(viewLifecycleOwner, { isEnabled ->
                if (isEnabled) {
                    showProgressBar(progress_bar, rv_covers)
                } else {
                    hideProgressBar(progress_bar, rv_covers)
                }
            })
    }

    private fun handlerOnErrorResult(viewModel: SelectCoverViewModel) {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, {
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