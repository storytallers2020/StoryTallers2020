package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomCharacter

@Dao
interface CharacterDao {

    //region Select

    @Query("SELECT * FROM RoomCharacter")
    fun getAll(): List<RoomCharacter>

    @Query("SELECT * FROM RoomCharacter WHERE id = :characterId LIMIT 1")
    fun getCharacterById(characterId: String): RoomCharacter?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: RoomCharacter)

    //endregion Insert

    //region Update

    @Update
    fun update(character: RoomCharacter)

    //endregion Update

    //region Delete

    @Delete
    fun delete(character: RoomCharacter)

    //endregion Delete
}