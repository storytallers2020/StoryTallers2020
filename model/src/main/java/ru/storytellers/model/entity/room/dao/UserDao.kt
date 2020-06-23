package ru.storytellers.model.entity.room.dao

import androidx.room.*
import ru.storytellers.model.entity.room.RoomUser

@Dao
interface UserDao {

    //region Select

    @Query("SELECT * FROM RoomUser")
    fun getAll(): List<RoomUser>

    @Query("SELECT * FROM RoomUser WHERE id = :userId LIMIT 1")
    fun getUserById(userId: String): RoomUser?

    @Query("SELECT * FROM RoomUser WHERE login = :login LIMIT 1")
    fun getUserByLogin(login: String): RoomUser?

    //endregion Select

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomUser)

    //endregion Insert

    //region Update

    @Update
    fun update(user: RoomUser)

    //endregion Update

    //region Delete

    @Delete
    fun delete(user: RoomUser)

    //endregion Delete
}