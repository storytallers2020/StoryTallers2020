package ru.storytellers.model.datasource.resourcestorage.storage

import android.content.Context
import ru.storytellers.model.entity.Character
import ru.storytellers.resources.R
import ru.storytellers.utils.resourceToString

class CharacterStorage(context: Context) {
    //region characterList...
    private val characterList: MutableList<Character> = mutableListOf(
        Character(
            1,
            context.getString(R.string.character_boy_name),
            resourceToString(context, R.drawable.ic_boy),
            resourceToString(context, R.drawable.ic_boy_selected)
        ),
        Character(
            2,
            context.getString(R.string.character_girl_name),
            resourceToString(context, R.drawable.ic_girl),
            resourceToString(context, R.drawable.ic_girl_selected)
        ),
        Character(
            3,
            context.getString(R.string.character_gnome_name),
            resourceToString(context, R.drawable.ic_gnome),
            resourceToString(context, R.drawable.ic_gnome_selected)
        ),
        Character(
            4,
            context.getString(R.string.character_fairy_name),
            resourceToString(context, R.drawable.ic_fairy),
            resourceToString(context, R.drawable.ic_fairy_selected)
        ),
        Character(
            5,
            context.getString(R.string.character_prince_name),
            resourceToString(context, R.drawable.ic_prince),
            resourceToString(context, R.drawable.ic_prince_selected)
        ),
        Character(
            6,
            context.getString(R.string.character_princess_name),
            resourceToString(context, R.drawable.ic_princess),
            resourceToString(context, R.drawable.ic_princess_selected)
        ),
        Character(
            7,
            context.getString(R.string.character_wizard_name),
            resourceToString(context, R.drawable.ic_wizard),
            resourceToString(context, R.drawable.ic_wizard_selected)
        ),
        Character(
            8,
            context.getString(R.string.character_witch_name),
            resourceToString(context, R.drawable.ic_witch),
            resourceToString(context, R.drawable.ic_witch_selected)
        ),
        Character(
            9,
            context.getString(R.string.character_robot_name),
            resourceToString(context, R.drawable.ic_robot),
            resourceToString(context, R.drawable.ic_robot_selected)
        ),
        Character(
            10,
            context.getString(R.string.character_cosmonaut_name),
            resourceToString(context, R.drawable.ic_cosmonaut),
            resourceToString(context, R.drawable.ic_cosmonaut_selected)
        ),
        Character(
            11,
            context.getString(R.string.character_pirate_name),
            resourceToString(context, R.drawable.ic_pirate),
            resourceToString(context, R.drawable.ic_pirate_selected)
        ),
        Character(
            12,
            context.getString(R.string.character_mermaid_name),
            resourceToString(context, R.drawable.ic_mermaid),
            resourceToString(context, R.drawable.ic_mermaid_selected)
        ),
        Character(
            13,
            context.getString(R.string.character_bear_name),
            resourceToString(context, R.drawable.ic_bear),
            resourceToString(context, R.drawable.ic_bear_selected)
        ),
        Character(
            14,
            context.getString(R.string.character_fox_name),
            resourceToString(context, R.drawable.ic_fox),
            resourceToString(context, R.drawable.ic_fox_selected)
        ),
        Character(
            15,
            context.getString(R.string.character_dragon_name),
            resourceToString(context, R.drawable.ic_dragon),
            resourceToString(context, R.drawable.ic_dragon_selected)
        ),
        Character(
            16,
            context.getString(R.string.character_fish_name),
            resourceToString(context, R.drawable.ic_fish),
            resourceToString(context, R.drawable.ic_fish_selected)
        ),
        Character(
            17,
            context.getString(R.string.character_skeleton_name),
            resourceToString(context, R.drawable.ic_skeleton),
            resourceToString(context, R.drawable.ic_skeleton_selected)
        ),
        Character(
            18,
            context.getString(R.string.character_ghost_name),
            resourceToString(context, R.drawable.ic_ghost),
            resourceToString(context, R.drawable.ic_ghost_selected)
        ),
        Character(
            19,
            context.getString(R.string.character_alien_name),
            resourceToString(context, R.drawable.ic_alien),
            resourceToString(context, R.drawable.ic_alien_selected)
        ),
        Character(
            20,
            context.getString(R.string.character_zombie_name),
            resourceToString(context, R.drawable.ic_zombie),
            resourceToString(context, R.drawable.ic_zombie_selected)
        )
    )
    //endregion

    fun insertOrReplace(character: Character) {
        characterList.find { it.id == character.id }?.let { foundCharacter ->
            characterList.remove(foundCharacter)
        }
        characterList.add(character)
    }

    fun getCharacterById(characterId: Long): Character? =
        characterList.find { it.id == characterId }

    fun getAll(): List<Character> = characterList

}