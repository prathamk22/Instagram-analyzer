package com.pratham.project.fileio.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pratham.project.fileio.data.remote.models.Caption
import com.pratham.project.fileio.data.remote.models.Location
import com.pratham.project.fileio.data.remote.models.UserXX

@Entity
data class FeedsEntity(
        @PrimaryKey val id: String,
        val carouselMediaCount: Int?,
        val caption: Caption?,
        val code: String?,
        val commentCount: Int?,
        val likeCount: Int?,
        val deviceTimeStamp: Long?,
        val hasAudio: Boolean?,
        val hasLiked: Boolean?,
        val location: Location?,
        val mediaType: Int?,
        val nextMaxId: String?,
        val pk: Long?,
        val userPk: Long?,
        val takenAt: Int?,
        val user: UserXX?,
        val viewCount: Double?,
)