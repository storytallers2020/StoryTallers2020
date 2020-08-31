package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_start.*
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.new_tale_button
import org.koin.android.ext.android.inject
import ru.storytellers.navigation.Screens
import ru.storytellers.utils.toastShowLong

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override  val model: StartViewModel by inject()


    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
        rules_game_text_view.setOnClickListener { navigateToRulesGame() }
        about_button.setOnClickListener { navigateToAboutScreen() }
        new_tale_button.setOnClickListener{ navigateToLevelScreen() }
        library_button.setOnClickListener { navigateToLibraryScreen() }
    }

    private fun navigateToLevelScreen() {
        router.navigateTo(Screens.LevelScreen())
    }

    override fun onStart() {
        super.onStart()
        model.getAllStory()
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.data?.let {listStoryLocal->
                if (listStoryLocal.isNotEmpty()) {
                    library_button.visibility= View.VISIBLE
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            activity?.let { context -> toastShowLong(context,context.getString(R.string.something_went_wrong)) }
        })
    }


    private fun navigateToRulesGame(){
        router.navigateTo(Screens.RulesGameScreen())
    }
    private fun navigateToAboutScreen(){
        router.navigateTo(Screens.AppInfoScreen())
    }

    private fun navigateToLibraryScreen(){
        router.navigateTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return false
    }
}