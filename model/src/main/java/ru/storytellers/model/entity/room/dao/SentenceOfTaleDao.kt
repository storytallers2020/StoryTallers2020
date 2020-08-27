package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomSentenceOfTale

@Dao
interface SentenceOfTaleDao {

    //region Select

    @Query("SELECT * FROM RoomSentenceOfTale")
    fun getAll(): List<RoomSentenceOfTale>

    @Query("SELECT * FROM RoomSentenceOfTale WHERE id = :sentenceId LIMIT 1")
    fun getSentenceById(sentenceId: Long): RoomSentenceOfTale?

    @Query("SELECT * FROM RoomSentenceOfTale WHERE storyId = :storyId")
    fun getAllStorySentence(storyId: Long): List<RoomSentenceOfTale>?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sentence: RoomSentenceOfTale)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRange(sentenceList: List<RoomSentenceOfTale>)

    //endregion Insert

    //region Update

    @Update
    fun update(sentenceId: RoomSentenceOfTale)

    //endregion Update

    //region Delete

    @Delete
    fun delete(sentenceId: RoomSentenceOfTale)

    @Delete
    fun delete(sentences: List<RoomSentenceOfTale>)

    //endregion Delete
}
