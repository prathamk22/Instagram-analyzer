package com.pratham.project.fileio.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.local.models.UserLocalCountsEntity

@Dao
interface UserLocalCountsDao : BaseDao<UserLocalCountsEntity> {

    @Query("SELECT * FROM UserLocalCountsEntity ORDER BY timeStamp DESC LIMIT 1")
    suspend fun getLatestAddedCounts(): UserLocalCountsEntity?

}