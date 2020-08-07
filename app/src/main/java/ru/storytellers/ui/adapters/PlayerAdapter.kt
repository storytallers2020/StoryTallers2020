package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import kotlinx.android.synthetic.main.item_user_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber


class PlayerAdapter(
    private val characterViewModel: CreateCharacterViewModel
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private val playersList = mutableListOf<Player>()

    fun setPlayersListData(listPlayer: List<Player>) {
        listPlayer?.let {
            playersList.clear()
            playersList.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_user_character_create,
                    parent, false
                )
        )
    }

    override fun getItemCount() = playersList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playersList[position])
    }

    inner class PlayerViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        fun bind(player: Player) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.player_name_tv.text = player.name
                player.character?.let {
                    resourceToUri(it.avatarUrl)?.let { uri ->
                        loadImage(uri, itemView.character)
                    }
                }
                itemView.remove_player_iv.setOnClickListener { removePlayer(player) }
            }
        }
    }

    private fun removePlayer(player: Player) {
        characterViewModel.removePlayer(player)
    }
}

