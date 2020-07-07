package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Character
import ru.storytellers.utils.loadImage
import timber.log.Timber

class ChooseCharacterAdapter():RecyclerView.Adapter<ChooseCharacterAdapter.ViewHolder>() {

    private val listCharacters = mutableListOf<Character>()


    fun setData(dataListCharacters: List<Character>?) {
        dataListCharacters?.let {
            listCharacters.clear()
            listCharacters.addAll(it)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_image_character_create,
                    parent, false
                )
        )
    }

    override fun getItemCount() = listCharacters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCharacters[position])
    }

    inner class ViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        fun bind(character:Character) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                loadImage(character.avatarUrl,itemView.image_character_iv)
                itemView.name_character_tv.text = character.name
                itemView.setOnClickListener {
                    Toast.makeText( // тост отладочный для наглядности
                        itemView.context,
                        "Выбран персонаж: ${character.name} id: ${character.id}",
                        Toast.LENGTH_LONG
                    ).show()
                    Timber.d("Выбран персонаж: ${character.name} id: ${character.id}")
                }


            }

        }
    }
}