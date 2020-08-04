package ru.storytellers.ui.fragments

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_character_create_v3.*
import kotlinx.android.synthetic.main.fragment_start.*
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.new_tale_button
import org.koin.android.ext.android.inject
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.utils.toastShowLong

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override  val model: StartViewModel by inject()
    private lateinit var startButton: MaterialButton
    private lateinit var listStory: List<Story>

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
        iniViewModel()
        model.getAllStory()
        rules_game_text_view.setOnClickListener { navigateToRulesGame() }
        new_tale_button.setOnClickListener{ navigateToLevelScreen() }
        library_button.setOnClickListener { navigateToLibraryScreen() }
    }

    private fun navigateToLevelScreen() {
        router.navigateTo(Screens.LevelScreen())
    }

    override fun iniViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.data?.let {listStoryLocal->
                if (listStoryLocal.isNotEmpty()) {
                    listStory=listStoryLocal
                    library_button.visibility= View.VISIBLE
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
            activity?.let { context -> toastShowLong(context,"Something went wrong") }
        })
    }


    private fun navigateToRulesGame(){
        router.navigateTo(Screens.RulesGameScreen())
    }

    private fun navigateToLibraryScreen(){
        router.navigateTo(Screens.LibraryScreen(listStory))
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}