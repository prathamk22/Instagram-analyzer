package com.pratham.project.fileio.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsernameEntity(
    @PrimaryKey val userId: Long?,
    val name: String?,
    val username: String?,
    val profilePic: String?,
    val usernameCookies: String?,
    val cityName: String?,
    val cityId: Int?,
    val followerCount: Int?,
    val followingCount: Int?,
    val hdProfilePic: String?,
    val isPrivate: Boolean?,
    val isVerified: Boolean?,
    val latitude: Double?,
    val longitude: Double?,
    val mediaCount: Int?,
    val email: String?,
    val publicPhoneCountryCode: String?,
    val publicPhoneNumber: String?,
    val zip: String?
)
