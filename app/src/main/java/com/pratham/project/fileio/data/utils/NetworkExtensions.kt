package com.pratham.project.fileio.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.models.FeedsEntity
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.local.models.UsernameEntity
import com.pratham.project.fileio.data.remote.models.Item
import com.pratham.project.fileio.data.remote.models.User
import com.pratham.project.fileio.data.remote.models.UserXX

fun User?.toLocalModel(sharedPreferences: PreferenceManager): UsernameEntity? {
    if (this == null) {
        return null
    }

    return UsernameEntity(
            userId = pk,
            name = fullName,
            username = username,
            profilePic = profilePicUrl,
            usernameCookies = sharedPreferences.loginCookies,
            cityName = cityName,
            cityId = cityId,
            followerCount = followerCount,
            followingCount = followingCount,
            hdProfilePic = hdProfilePicUrlInfo?.url,
            isPrivate = isPrivate,
            isVerified = isVerified,
            latitude = latitude,
            longitude = longitude,
            mediaCount = mediaCount,
            email = publicEmail,
            publicPhoneCountryCode = publicPhoneCountryCode,
            publicPhoneNumber = publicPhoneNumber,
            zip = zip
    )
}

fun List<UserXX>?.toUserXXX(): List<UserXXX>? {
    if (isNullOrEmpty())
        return null

    return map {
        UserXXX(
                accountBadges = it.accountBadges,
                friendshipStatus = it.friendshipStatus,
                fullName = it.fullName,
                hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
                isFavorite = it.isFavorite,
                isPrivate = it.isPrivate,
                isUnpublished = it.isUnpublished,
                isVerified = it.isVerified,
                pk = it.pk,
                profilePicId = it.profilePicId,
                profilePicUrl = it.profilePicUrl,
                storyReelMediaIds = it.storyReelMediaIds,
                username = it.username
        )
    }
}

fun List<UserXXX>?.toUserXX(): List<UserXX>? {
    if (isNullOrEmpty())
        return null

    return map {
        UserXX(
                accountBadges = it.accountBadges,
                friendshipStatus = it.friendshipStatus,
                fullName = it.fullName,
                hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
                isFavorite = it.isFavorite,
                isPrivate = it.isPrivate,
                isUnpublished = it.isUnpublished,
                isVerified = it.isVerified,
                pk = it.pk,
                profilePicId = it.profilePicId,
                profilePicUrl = it.profilePicUrl,
                storyReelMediaIds = it.storyReelMediaIds,
                username = it.username
        )
    }
}

fun List<Item>?.toLocalUserFeed(): List<FeedsEntity>? {
    if (this.isNullOrEmpty()) {
        return null
    }

    return map {
        FeedsEntity(
                id = it.id.toString(),
                carouselMediaCount = it.carouselMediaCount,
                caption = it.caption,
                code = it.code,
                commentCount = it.commentCount,
                likeCount = it.likeCount,
                deviceTimeStamp = it.deviceTimestamp,
                hasAudio = it.hasAudio,
                hasLiked = it.hasLiked,
                location = it.location,
                mediaType = it.mediaType,
                nextMaxId = it.nextMaxId,
                pk = it.pk,
                userPk = it.user?.pk,
                takenAt = it.takenAt,
                user = it.user,
                viewCount = it.viewCount
        )
    }
}

fun Any?.toJson(): String {
    return Gson().toJson(this)
}

fun <T> String?.jsonToObject(): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, type)
}
