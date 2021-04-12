package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Player
import ru.storytellers.model.image.IImageLoader


class TeamCharacterAdapter(
    val imageLoader: IImageLoader,
    val onRemovePlayerListener: (player: Player) -> Unit
) : RecyclerView.Adapter<TeamCharacterAdapter.TeamViewHolder>() {
    private val playersList = mutableListOf<Player>()

    fun setPlayersListData(newPlayers: List<Player>) {
        playersList.clear()
        playersList.addAll(newPlayers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_user_character_create,
                    parent, false
                )
        )
    }

    override fun getItemCount() = playersList.count()

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(playersList[position])
    }

    inner class TeamViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        fun bind(player: Player) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.player_name_tv.text = player.name
                player.character?.avatarUrl?.let { url ->
                    imageLoader.loadInto(url, R.drawable.avatar_stub, itemView.character)
//                    url?.let { resourceToUri(url)?.let { uri ->
//                        loadImage(uri, itemView.character)
//                    } }
                }
                itemView.remove_player_iv.setOnClickListener { onRemovePlayerListener(player) }
            }

        }
    }
}