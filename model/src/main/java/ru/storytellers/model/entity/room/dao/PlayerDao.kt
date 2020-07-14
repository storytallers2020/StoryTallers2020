package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomPlayer

@Dao
interface PlayerDao {

    //region Select

    @Query("SELECT * FROM RoomPlayer")
    fun getAll(): List<RoomPlayer>?

    @Query("SELECT * FROM RoomPlayer WHERE id = :playerId LIMIT 1")
    fun getPlayerById(playerId: Long): RoomPlayer?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: RoomPlayer)

    //endregion Insert

    //region Update

    @Update
    fun update(player: RoomPlayer)

    //endregion Update

    //region Delete

    @Delete
    fun delete(player: RoomPlayer)

    //endregion Delete
}
