package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class UserXXXX(
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("is_private") val isPrivate: Boolean? = null,
    @SerializedName("is_verified") val isVerified: Boolean? = null,
    @SerializedName("pk") val pk: Long? = null,
    @SerializedName("profile_pic_id") val profilePicId: String? = null,
    @SerializedName("profile_pic_url") val profilePicUrl: String? = null,
    @SerializedName("username") val username: String? = null
)