package com.pratham.project.fileio.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.local.models.FeedsEntity

@Dao
interface FeedsDao : BaseDao<FeedsEntity> {

    @Query("SELECT * FROM FeedsEntity WHERE userPk = :userPk ORDER BY takenAt DESC LIMIT 20")
    fun getUserPostsLD(userPk: Long): LiveData<List<FeedsEntity>>

    @Query("SELECT * FROM FeedsEntity WHERE userPk = :userPk")
    suspend fun getUserPosts(userPk: Long): List<FeedsEntity>

    @Query("DELETE FROM FeedsEntity")
    suspend fun deleteAll()

}
