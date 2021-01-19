package ru.storytellers.model.entity.room.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS RoomCachedImage;")
        database.execSQL("CREATE TABLE IF NOT EXISTS RoomCachedImage (" +
                "url TEXT NOT NULL PRIMARY KEY," +
                "localPath TEXT NOT NULL);")
    }
}