package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Character
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.CreateCharacterViewModel
import timber.log.Timber

class ChooseCharacterAdapter(
    private val characterViewModel: CreateCharacterViewModel,
    private val playerCreator: PlayerCreator
):RecyclerView.Adapter<ChooseCharacterAdapter.ViewHolder>() {

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
                resourceToUri(character.avatarUrl)?.let {
                    loadImage(it, itemView.image_character_iv)
                }
                itemView.name_character_tv.text = character.name
                itemView.setOnClickListener {
                    addPlayer(character)
                    characterViewModel.setFlagActive(true)
                    Timber.d("Выбран персонаж: ${character.name} id: ${character.id}")
                }
            }
        }
        private fun addPlayer(character:Character){
            playerCreator.setCharacterOfPlayer(character)
            val player= playerCreator.createPlayer()
            characterViewModel.addPlayer(player)
        }
    }
}