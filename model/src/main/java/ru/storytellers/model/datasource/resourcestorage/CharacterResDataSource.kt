package ru.storytellers.model.datasource.resourcestorage

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.resources.R
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.utils.resourceToString
import ru.storytellers.model.entity.*

class CharacterResDataSource(context: Context) : ICharacterDataSource {
    //region characterList...
    private val characterList: MutableList<Character> = mutableListOf(
        Character(
            1,
            context.getString(R.string.character_boy_name),
            resourceToString(context, R.drawable.ic_boy)
        ),
        Character(
            2,
            context.getString(R.string.character_fairy_name),
            resourceToString(context, R.drawable.ic_fairy)
        ),
        Character(
            3,
            context.getString(R.string.character_girl_name),
            resourceToString(context, R.drawable.ic_girl)
        ),
        Character(
            4,
            context.getString(R.string.character_knight_name),
            resourceToString(context, R.drawable.ic_knight)
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
            context.getString(R.string.character_step_mother_name),
            resourceToString(context, R.drawable.ic_stepmother)
        ),
        Character(
            12,
            context.getString(R.string.character_wizard_name),
            resourceToString(context, R.drawable.ic_wizard)
        )
    )
    //endregion

    override fun insertOrReplace(character: Character): Completable =
        Completable.fromAction {
            characterList.find { it.id == character.id }?.let { foundCharacter ->
                characterList.remove(foundCharacter)
            }
            characterList.add(character)
        }

    override fun getCharacterById(characterId: Long): Single<Character> =
        Single.create { emitter ->
            characterList.find { character ->
                character.id == characterId
            }?.let {
                emitter.onSuccess(it)
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }

    override fun getAll(): Single<List<Character>> =
        Single.create { emitter ->
            emitter.onSuccess(characterList)
        }

}