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

class CharacterCreateAdapter(val itemClickListener: (character: Character,itemRecycler:View, position:Int ) -> Unit?)
    : RecyclerView.Adapter<CharacterCreateAdapter.CCViewHolder>(){
    private val listCharacters = mutableListOf<Character>()
    fun setData(dataListCharacters: List<Character>?) {
        dataListCharacters?.let {
            listCharacters.clear()
            listCharacters.addAll(it)
        }
        notifyDataSetChanged()
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

    override fun getItemCount()=listCharacters.count()

    override fun onBindViewHolder(holder: CCViewHolder, position: Int) {
        holder.bind(listCharacters[position])
    }

    inner class CCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                resourceToUri(character.avatarUrl)?.let {
                    loadImage(it, itemView.image_character_iv)
                }
                itemView.name_character_tv.text = character.name
                itemView.setOnClickListener {itemClickListener(character,itemView,layoutPosition)}
            }
        }
    }
}