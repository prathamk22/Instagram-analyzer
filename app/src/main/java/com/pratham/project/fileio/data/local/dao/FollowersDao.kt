package com.pratham.project.fileio.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.pratham.project.fileio.data.remote.models.UserXX

@Dao
interface FollowersDao : BaseDao<UserXX> {

    @Query("SELECT * FROM Followers WHERE connectedToUserPk = :userId")
    suspend fun getAllFollowers(userId: Long): List<UserXX>

}