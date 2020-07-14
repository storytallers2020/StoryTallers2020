package ru.storytellers.utils

import androidx.room.TypeConverter
import ru.storytellers.model.entity.ContentTypeEnum

class EnumHelper {
    @TypeConverter
    fun toContentType(value: String) = enumValueOf<ContentTypeEnum>(value)

    @TypeConverter
    fun fromContentType(value: ContentTypeEnum) = value.name
}