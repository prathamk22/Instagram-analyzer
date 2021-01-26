package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class UserXXXXX(
    @SerializedName("account_badges") val accountBadges: List<Any>? = null,
    @SerializedName("friendship_status") val friendshipStatus: FriendshipStatusX? = null,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("has_anonymous_profile_picture") val hasAnonymousProfilePicture: Boolean? = null,
    @SerializedName("is_favorite") val isFavorite: Boolean? = null,
    @SerializedName("is_private") val isPrivate: Boolean? = null,
    @SerializedName("is_unpublished") val isUnpublished: Boolean? = null,
    @SerializedName("is_verified") val isVerified: Boolean? = null,
    @SerializedName("latest_reel_media") val latestReelMedia: Int? = null,
    @SerializedName("pk") val pk: Long? = null,
    @SerializedName("profile_pic_id") val profilePicId: String? = null,
    @SerializedName("profile_pic_url") val profilePicUrl: String? = null,
    @SerializedName("story_reel_media_ids") val storyReelMediaIds: List<Any>? = null,
    @SerializedName("username") val username: String? = null
)