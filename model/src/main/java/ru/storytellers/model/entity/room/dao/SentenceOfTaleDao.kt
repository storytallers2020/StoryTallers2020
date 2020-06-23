package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomCharacter
import ru.storytellers.model.entity.room.RoomLocation
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.RoomStory

@Dao
interface SentenceOfTaleDao {

    //region Select

    @Query("SELECT * FROM RoomSentenceOfTale")
    fun getAll(): List<RoomSentenceOfTale>

    @Query("SELECT * FROM RoomSentenceOfTale WHERE id = :sentenceId LIMIT 1")
    fun getSentenceById(sentenceId: String): RoomSentenceOfTale?

    @Query("SELECT * FROM RoomSentenceOfTale WHERE storyId = :storyId")
    fun getAllStorySentence(storyId: String): List<RoomSentenceOfTale>?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sentenceId: RoomSentenceOfTale)

    //endregion Insert

    //region Update

    @Update
    fun update(sentenceId: RoomSentenceOfTale)

    //endregion Update

    //region Delete

    @Delete
    fun delete(sentenceId: RoomSentenceOfTale)

    //endregion Delete
}