package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomStory

@Dao
interface StoryDao {

    //region Select

    @Query("SELECT * FROM RoomStory")
    fun getAll(): List<RoomStory>?

    @Query("SELECT * FROM RoomStory WHERE id = :storyId LIMIT 1")
    fun getStoryById(storyId: Long): RoomStory?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(story: RoomStory)

    //endregion Insert

    //region Update

    @Update
    fun update(story: RoomStory)
    @Query("UPDATE  RoomStory SET name = :titleStory WHERE id = :storyId")
    fun updateTitleStory(titleStory: String, storyId: Long): Int

    //endregion Update

    //region Delete

    @Delete
    fun delete(story: RoomStory): Int

    //@Delete
    @Query("DELETE FROM RoomStory WHERE id = :storyId")
    fun deleteById(storyId: Long): Int

    //endregion Delete
}
