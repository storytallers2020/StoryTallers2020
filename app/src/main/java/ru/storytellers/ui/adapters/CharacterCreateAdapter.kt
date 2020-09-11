package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Character
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class CharacterCreateAdapter(val itemClickListener: (character: Character, position:Int ) -> Unit?)
    : RecyclerView.Adapter<CharacterCreateAdapter.CCViewHolder>(){

    private var listCharacters = mutableListOf<Character>()
    var selectedPosition = -1

    fun setData(dataListCharacters: List<Character>?) {
        dataListCharacters?.let {
            this.listCharacters = it as MutableList<Character>
            notifyDataSetChanged()
            selectedPosition = -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CCViewHolder {
        return CCViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_image_character_create,
                    parent, false
                )
        )
    }

    override fun getItemCount()= listCharacters.count()

    override fun onBindViewHolder(holder: CCViewHolder, position: Int) {
        holder.itemView.isSelected = selectedPosition == position
        holder.bind(listCharacters[position])
    }

    inner class CCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            with(itemView) {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    val avatar = if (isSelected) character.avatarUrlSelected else character.avatarUrl
                    resourceToUri(avatar)?.let { loadImage(it, image_character_iv) }
                    setOnClickListener { itemClickListener(character, layoutPosition) }
                }
            }
        }
    }
}