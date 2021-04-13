package ru.storytellers.ui.fragments

import android.view.View
import kotlinx.android.synthetic.main.fragment_character_team.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.adapters.TeamCharacterAdapter
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.CustomAlertDialog
import ru.storytellers.utils.DialogCaller
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.TeamCharacterViewModel

private const val FRAGMENT_DIALOG_TAG = "team-5d62-46bf-ab6"

class TeamCharacterFragment : BaseFragment<DataModel>(), DialogCaller {
    override val model: TeamCharacterViewModel by inject()
    override val layoutRes = R.layout.fragment_character_team
    private var character: Player? = null
    private val teamAdapter: TeamCharacterAdapter by lazy {
        TeamCharacterAdapter(imageLoader, onRemovePlayerListener)
    }
    private var sizeListPlayer = 0

    companion object {
        fun newInstance() = TeamCharacterFragment()
    }

    private val onRemovePlayerListener = { player: Player ->
        character = player
        createAndShowAlertDialog()
    }

    override fun init() {
        btn_next.setOnClickListener { navigateToLocationScreen() }
        back_button_character.setOnClickListener { navigateToLevelScreen() }
        player_list_rv.adapter = teamAdapter
        initViewModel()
    }

    override fun initViewModel() {
        btn_add_player.setOnClickListener { backToCharacterCreateScreen() }
    }

    override fun onStart() {
        super.onStart()
        model.subscribeOnPlayers().observe(
            viewLifecycleOwner,
            {
                sizeListPlayer = it.size
                setPlayersToPlayerAdapter(it)
                if (it.size >= 8) {
                    btn_add_player.visibility = View.GONE
                    max_players_notification.visibility = View.VISIBLE
                } else {
                    btn_add_player.visibility = View.VISIBLE
                    max_players_notification.visibility = View.GONE
                }
            })
    }

    private fun setPlayersToPlayerAdapter(it: List<Player>) {
        teamAdapter.setPlayersListData(it)
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }

    private fun backToCharacterCreateScreen() {
        model.onGotoCharacterScreen()
        router.navigateTo(Screens.CharacterCreateScreen())
    }

    private fun navigateToLevelScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.navigateTo(Screens.LevelScreen())
    }

    private fun navigateToLocationScreen() {
        if (sizeListPlayer < 2) {
            context?.let { context ->
                toastShowLong(
                    context,
                    context.getString(R.string.msg_need_more_players)
                )
            }
        } else {
            model.onGotoLocationScreen()
            router.navigateTo(Screens.LocationScreen())
        }
    }

    private fun removeCharacter() {
        character?.let { model.removePlayer(it) }
    }

    private fun createAndShowAlertDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            CustomAlertDialog(this, R.string.dialog_character).show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }

    override fun onDialogPositiveButton(tag: String?) {
        removeCharacter()
    }

    override fun onDialogNegativeButton(tag: String?) {
        // do nothing
    }

}