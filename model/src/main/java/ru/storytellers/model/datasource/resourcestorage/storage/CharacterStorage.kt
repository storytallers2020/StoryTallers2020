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
            resourceToString(context, R.drawable.ic_boy)
        ),
        Character(
            2,
            context.getString(R.string.character_girl_name),
            resourceToString(context, R.drawable.ic_girl)
        ),
        Character(
            3,
            context.getString(R.string.character_knight_name),
            resourceToString(context, R.drawable.ic_knight)
        ),
        Character(
            4,

            context.getString(R.string.character_fairy_name),
            resourceToString(context, R.drawable.ic_fairy)
        ),
        Character(
            5,
            context.getString(R.string.character_prince_name),
            resourceToString(context, R.drawable.ic_prince)
        ),
        Character(
            6,
            context.getString(R.string.character_princess_name),
            resourceToString(context, R.drawable.ic_princess)
        ),
        Character(
            7,
            context.getString(R.string.character_robot_boy_name),
            resourceToString(context, R.drawable.ic_robot_boy)
        ),
        Character(
            8,
            context.getString(R.string.character_robot_girl_name),
            resourceToString(context, R.drawable.ic_robot_girl)
        ),
        Character(
            9,
            context.getString(R.string.character_space_boy_name),
            resourceToString(context, R.drawable.ic_space_boy)
        ),
        Character(
            10,
            context.getString(R.string.character_space_girl_name),
            resourceToString(context, R.drawable.ic_space_girl)
        ),
        Character(
            11,
            context.getString(R.string.character_wizard_name),
            resourceToString(context, R.drawable.ic_wizard)
        ),
        Character(
            12,
            context.getString(R.string.character_step_mother_name),
            resourceToString(context, R.drawable.ic_stepmother)
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