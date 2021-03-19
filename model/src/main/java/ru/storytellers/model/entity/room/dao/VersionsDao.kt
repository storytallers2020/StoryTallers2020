package ru.storytellers.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.room.RoomVersions

@Dao
interface VersionsDao {

    @Query("SELECT * FROM RoomVersions LIMIT 1")
    fun getAll(): RoomVersions?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(versions: RoomVersions)
}
