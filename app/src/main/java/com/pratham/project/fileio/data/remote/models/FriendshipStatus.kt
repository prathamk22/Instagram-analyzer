package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class FriendshipStatus(
    @SerializedName("following") val following: Boolean? = null,
    @SerializedName("is_bestie") val isBestie: Boolean? = null,
    @SerializedName("is_restricted") val isRestricted: Boolean? = null,
    @SerializedName("outgoing_request") val outgoingRequest: Boolean? = null
)