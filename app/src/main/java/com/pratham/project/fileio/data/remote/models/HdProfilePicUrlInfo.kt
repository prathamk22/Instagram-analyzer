package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HdProfilePicUrlInfo(
        @SerializedName("height") @Expose val height: Int? = null,
        @SerializedName("url") @Expose val url: String? = null,
        @SerializedName("width") @Expose val width: Int? = null
)