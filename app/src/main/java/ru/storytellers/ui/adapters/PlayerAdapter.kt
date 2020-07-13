package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import kotlinx.android.synthetic.main.item_user_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Character
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import timber.log.Timber


class PlayerAdapter:RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>(){

    private val playersList = mutableListOf<String>()

    fun setDataToPlayerAdapter(namePlayerList: List<String>?) {
        namePlayerList?.let {
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

    override fun getItemCount()=playersList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playersList[position])
    }

    inner class PlayerViewHolder(container: View) : RecyclerView.ViewHolder(container){
        fun bind(namePlayer:String) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.player_name_tv.text=namePlayer
            }
        }
    }
}
