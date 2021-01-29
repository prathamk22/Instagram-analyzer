package com.pratham.project.fileio.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.remote.models.Caption
import com.pratham.project.fileio.data.remote.models.FriendshipStatus
import com.pratham.project.fileio.data.remote.models.Location
import com.pratham.project.fileio.data.remote.models.UserXX
import com.pratham.project.fileio.data.utils.jsonToObject
import com.pratham.project.fileio.data.utils.toJson
import java.util.*

object DateTypeConverter {

    @TypeConverter
    fun toFriendshipStatusJson(value: String?): FriendshipStatus? {
        return value?.let { it.jsonToObject<FriendshipStatus>() }
    }

    @TypeConverter
    fun toFriendshipStatusString(value: FriendshipStatus?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toListString(value: String?): List<String>? {
        return value?.let { value.jsonToObject<List<String>>() }
    }

    @TypeConverter
    fun toListString(value: List<String>?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toListAny(value: String?): List<Any>? {
        return value?.let { value.jsonToObject<List<Any>>() }
    }

    @TypeConverter
    fun toListAny(value: List<Any>?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toListInt(value: String?): List<Int>? {
        return value?.let { value.jsonToObject<List<Int>>() }
    }

    @TypeConverter
    fun toListInt(value: List<Int>?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toListLong(value: String?): List<Long>? {
        return value?.let { value.jsonToObject<List<Long>>() }
    }

    @TypeConverter
    fun toListLong(value: List<Long>?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toUserXXString(value: String?): UserXX? {
        val type = object : TypeToken<UserXX>(){}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toUserXXString(value: UserXX?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toUserXXXString(value: String?): UserXXX? {
        return value?.let { value.jsonToObject<UserXXX>() }
    }

    @TypeConverter
    fun toUserXXString(value: UserXXX?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toCaptionString(value: String?): Caption? {
        return value?.let { value.jsonToObject<Caption>() }
    }

    @TypeConverter
    fun toCaptionString(value: Caption?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toLocationString(value: String?): Location? {
        val type = object : TypeToken<Location>(){}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toLocationString(value: Location?): String? {
        return value?.let { value.toJson() }
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}