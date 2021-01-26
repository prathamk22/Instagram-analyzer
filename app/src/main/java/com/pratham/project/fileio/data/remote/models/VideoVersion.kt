package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class VideoVersion(
    @SerializedName("height") val height: Int? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("width") val width: Int? = null
)