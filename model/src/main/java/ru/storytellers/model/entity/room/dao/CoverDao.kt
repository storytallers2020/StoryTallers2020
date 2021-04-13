package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomCover

@Dao
interface CoverDao {

    //region Select

    @Query("SELECT * FROM RoomCover")
    fun getAll(): List<RoomCover>?

    @Query("SELECT * FROM RoomCover WHERE id = :coverId LIMIT 1")
    fun getCoverById(coverId: Long): RoomCover?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cover: RoomCover)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coverList: List<RoomCover>)

    //endregion Insert

    //region Update

    @Update
    fun update(cover: RoomCover)

    //endregion Update

    //region Delete

    @Delete
    fun delete(cover: RoomCover)

    //endregion Delete
}