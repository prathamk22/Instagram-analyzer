package com.pratham.project.fileio.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.local.models.FeedsEntity

@Dao
interface FeedsDao : BaseDao<FeedsEntity> {

    @Query("SELECT * FROM FeedsEntity WHERE userPk = :userPk ORDER BY takenAt ASC")
    fun getUserPosts(userPk: Long): LiveData<List<FeedsEntity>>

}