package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomWord

@Dao
interface WordDao {

    @Query("SELECT * FROM RoomWord WHERE lang = :lang")
    fun getAll(lang: String): List<RoomWord>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wordList: List<RoomWord>)
}