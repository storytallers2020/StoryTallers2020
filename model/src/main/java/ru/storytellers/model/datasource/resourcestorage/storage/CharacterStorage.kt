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
            context.getString(R.string.character_knight_name),
            resourceToString(context, R.drawable.ic_knight),
            resourceToString(context, R.drawable.ic_knight_selected)
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
            context.getString(R.string.character_robot_boy_name),
            resourceToString(context, R.drawable.ic_robot_boy),
            resourceToString(context, R.drawable.ic_robot_boy_selected)
        ),
        Character(
            8,
            context.getString(R.string.character_robot_girl_name),
            resourceToString(context, R.drawable.ic_robot_girl),
            resourceToString(context, R.drawable.ic_robot_girl_selected)
        ),
        Character(
            9,
            context.getString(R.string.character_space_boy_name),
            resourceToString(context, R.drawable.ic_space_boy),
            resourceToString(context, R.drawable.ic_space_boy_selected)
        ),
        Character(
            10,
            context.getString(R.string.character_space_girl_name),
            resourceToString(context, R.drawable.ic_space_girl),
            resourceToString(context, R.drawable.ic_space_girl_selected)
        ),
        Character(
            11,
            context.getString(R.string.character_wizard_name),
            resourceToString(context, R.drawable.ic_wizard),
            resourceToString(context, R.drawable.ic_wizard_selected)
        ),
        Character(
            12,
            context.getString(R.string.character_step_mother_name),
            resourceToString(context, R.drawable.ic_stepmother),
            resourceToString(context, R.drawable.ic_stepmother_selected)
        ),
        Character(
            13,
            context.getString(R.string.character_fire_bird_name),
            resourceToString(context, R.drawable.ic_firebird),
            resourceToString(context, R.drawable.ic_firebird_selected)
        ),
        Character(
            14,
            context.getString(R.string.character_squirrel_name),
            resourceToString(context, R.drawable.ic_squirrel),
            resourceToString(context, R.drawable.ic_squirrel_selected)
        ),
        Character(
            15,
            context.getString(R.string.character_hare_name),
            resourceToString(context, R.drawable.ic_hare),
            resourceToString(context, R.drawable.ic_hare_selected)
        ),
        Character(
            16,
            context.getString(R.string.character_fox_name),
            resourceToString(context, R.drawable.ic_fox),
            resourceToString(context, R.drawable.ic_fox_selected)
        ),

        Character(
            17,
            context.getString(R.string.character_wolf_name),
            resourceToString(context, R.drawable.ic_wolf),
            resourceToString(context, R.drawable.ic_wolf_selected)
        ),
        Character(
            18,
            context.getString(R.string.character_bear_name),
            resourceToString(context, R.drawable.ic_bear),
            resourceToString(context, R.drawable.ic_bear_selected)
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