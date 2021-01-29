package com.pratham.project.fileio.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserLocalCountsEntity(
        val userTotalLikesCount: Int,
        val userTotalCommentsCount: Int,
        val userFollowesCount: Int,
        val userFollowingsCount: Int,
        val userPostsCount: Int,
        @PrimaryKey val timeStamp: Date = Date(System.currentTimeMillis())
)