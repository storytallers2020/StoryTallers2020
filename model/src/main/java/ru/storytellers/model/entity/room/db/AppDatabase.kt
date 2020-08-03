package ru.storytellers.model.entity.room.db

import androidx.room.Database
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
}
