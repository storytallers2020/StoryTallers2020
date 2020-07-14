package ru.storytellers.model.entity.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.storytellers.model.entity.room.*
import ru.storytellers.model.entity.room.dao.*
import ru.storytellers.utils.EnumHelper

@Database(
    entities = [
        RoomCharacter::class,
        RoomLocation::class,
        RoomSentenceOfTale::class,
        RoomStory::class,
        RoomUser::class,
        RoomPlayer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(EnumHelper::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val locationDao: LocationDao
    abstract val sentenceOfTaleDao: SentenceOfTaleDao
    abstract val storyDao: StoryDao
    abstract val userDao: UserDao
    abstract val playerDao: PlayerDao

    companion object {
        private const val DB_NAME = "database.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context) = instance
            ?: let {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, DB_NAME
                )
                    //.addMigrations(MIGRATION_1_2)
                    .build()
            }
    }
}
