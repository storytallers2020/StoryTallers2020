package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomVersions

@Dao
interface VersionsDao {

    @Query("SELECT * FROM RoomVersions LIMIT 1")
    fun getAll(): RoomVersions?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(versions: RoomVersions)

    @Query("DELETE FROM RoomVersions")
    fun deleteAll()
}
