package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_team.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.TeamCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.TeamCharacterViewModel

class TeamCharacterFragment : BaseFragment<DataModel>() {
    override val model: TeamCharacterViewModel by inject()
    override val layoutRes = R.layout.fragment_character_team
    private val teamAdapter: TeamCharacterAdapter by lazy {
        TeamCharacterAdapter(
            onRemovePlayerListener
        )
    }
    private var sizeListPlayer=0

    companion object {
        fun newInstance() = TeamCharacterFragment()
    }

    private val onRemovePlayerListener = { player: Player ->
        model.removePlayer(player)
    }


    override fun init() {
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener { navigateToLevelScreen() }
        player_list_rv.adapter = teamAdapter
        iniViewModel()
    }

    override fun iniViewModel() {
        btn_add_player.setOnClickListener { backToCharacterCreateScreen() }
    }

    override fun onStart() {
        super.onStart()
        model.subscribeOnPlayers().observe(
            viewLifecycleOwner,
            Observer {
                sizeListPlayer=it.size
                setPlayersToPlayerAdapter(it)
                if (it.size >= 8 ) {
                    context?.let { context -> toastShowLong(context, context.getString(R.string.player_limit_reached)) }
                    btn_add_player.visibility = View.INVISIBLE
                } else btn_add_player.visibility = View.VISIBLE
            })
    }

    private fun setPlayersToPlayerAdapter(it: List<Player>) {
        teamAdapter.setPlayersListData(it)
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    private fun backToCharacterCreateScreen() {
        router.navigateTo(Screens.CharacterCreateScreen())
    }

    private fun navigateToLevelScreen() {
        router.navigateTo(Screens.LevelScreen())

    }

    private fun navigateToLocationScreen() {
        if (sizeListPlayer < 2 ){
            context?.let { context -> toastShowLong(context, context.getString(R.string.player_cant_be_alone)) }
        } else router.navigateTo(Screens.LocationScreen())
    }
}