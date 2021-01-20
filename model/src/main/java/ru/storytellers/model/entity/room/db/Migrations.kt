package ru.storytellers.model.entity.room.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.annotations.Expose

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS RoomCachedImage;")
        database.execSQL("CREATE TABLE IF NOT EXISTS RoomCachedImage (" +
                "url TEXT NOT NULL PRIMARY KEY," +
                "localPath TEXT NOT NULL);")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS RoomCover;")
        database.execSQL("CREATE TABLE IF NOT EXISTS RoomCover (" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "imageUrl TEXT NOT NULL," +
                "imagePreview TEXT NOT NULL);")
    }
}