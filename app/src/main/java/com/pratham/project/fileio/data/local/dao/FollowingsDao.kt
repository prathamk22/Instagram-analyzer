package com.pratham.project.fileio.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.local.models.UserXXX

@Dao
interface FollowingsDao : BaseDao<UserXXX> {

    @Query("SELECT * FROM Followings")
    suspend fun getAllFollowings(): List<UserXXX>

    @Query("DELETE FROM Followings")
    suspend fun deleteAll()


}