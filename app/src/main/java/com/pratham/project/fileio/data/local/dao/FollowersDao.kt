package com.pratham.project.fileio.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.remote.models.UserXX

@Dao
interface FollowersDao : BaseDao<UserXX> {

    @Query("SELECT * FROM Followers")
    suspend fun getAllFollowers(): List<UserXX>

    @Query("DELETE FROM Followers")
    suspend fun deleteAll()

}