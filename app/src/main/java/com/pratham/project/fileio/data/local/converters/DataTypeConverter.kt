package com.pratham.project.fileio.data.local.converters

import androidx.room.TypeConverter
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.remote.models.FriendshipStatus
import com.pratham.project.fileio.data.remote.models.UserXX
import com.pratham.project.fileio.data.utils.jsonToObject
import com.pratham.project.fileio.data.utils.toJson

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
    fun toUserXXString(value: String?): UserXX? {
        return value?.let { value.jsonToObject<UserXX>() }
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

}