package com.pratham.project.fileio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pratham.project.fileio.data.local.converters.DateTypeConverter
import com.pratham.project.fileio.data.local.dao.FeedsDao
import com.pratham.project.fileio.data.local.dao.FollowersDao
import com.pratham.project.fileio.data.local.dao.FollowingsDao
import com.pratham.project.fileio.data.local.dao.UsernameDao
import com.pratham.project.fileio.data.local.models.FeedsEntity
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.local.models.UsernameEntity
import com.pratham.project.fileio.data.remote.models.UserXX

@Database(
        entities = [UsernameEntity::class, UserXX::class, UserXXX::class, FeedsEntity::class],
        exportSchema = true,
        version = 3
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUsernameDao(): UsernameDao

    abstract fun getFollowersDao(): FollowersDao

    abstract fun getFollowingDao(): FollowingsDao

    abstract fun getFeedsDao(): FeedsDao
}