package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomCachedImage

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomImage: RoomCachedImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomImageList: List<RoomCachedImage>)

    @Update
    fun update(roomImage: RoomCachedImage)

    @Update
    fun update(roomImageList: List<RoomCachedImage>)

    @Delete
    fun delete(roomImage: RoomCachedImage)

    @Delete
    fun delete(roomImageList: List<RoomCachedImage>)

    @Query("DELETE FROM RoomCachedImage")
    fun clear()

    @Query("SELECT * FROM RoomCachedImage")
    fun getAll(): List<RoomCachedImage>

    @Query("SELECT * FROM RoomCachedImage WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): RoomCachedImage?
}