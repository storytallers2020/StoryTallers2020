package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomLocation

@Dao
interface LocationDao {

    //region Select

    @Query("SELECT * FROM RoomLocation")
    fun getAll(): List<RoomLocation>?

    @Query("SELECT * FROM RoomLocation WHERE id = :locationId LIMIT 1")
    fun getLocationById(locationId: Long): RoomLocation?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: RoomLocation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationList: List<RoomLocation>)

    //endregion Insert

    //region Update

    @Update
    fun update(location: RoomLocation)

    //endregion Update

    //region Delete

    @Delete
    fun delete(location: RoomLocation)

    //endregion Delete
}
