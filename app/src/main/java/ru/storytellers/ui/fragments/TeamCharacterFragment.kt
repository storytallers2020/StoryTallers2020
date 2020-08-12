package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_team.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.TeamCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.TeamCharacterViewModel

class TeamCharacterFragment : BaseFragment<DataModel>() {
    override val model: TeamCharacterViewModel by inject()
    override val layoutRes = R.layout.fragment_character_team
    private val teamAdapter: TeamCharacterAdapter by lazy {
        TeamCharacterAdapter(
            onRemovePlayerListener
        )
    }

    companion object {
        fun newInstance() = TeamCharacterFragment()
    }

    private val onRemovePlayerListener = { player: Player ->
        model.removePlayer(player)
    }


    override fun init() {
        btn_next.setOnClickListener { navigateToLocationScreen() }
        player_list_rv.adapter = teamAdapter
        iniViewModel()
    }

    override fun iniViewModel() {
        btn_add_player.setOnClickListener { backToCharacterCreateScreen() }
    }

    override fun onResume() {
        super.onResume()
        model.subscribeOnPlayers().observe(
            viewLifecycleOwner,
            Observer {
                setPlayersToPlayerAdapter(it)
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
        router.backTo(Screens.CharacterCreateScreen())
    }

    private fun navigateToLocationScreen() {
        router.navigateTo(Screens.LocationScreen())
    }
}