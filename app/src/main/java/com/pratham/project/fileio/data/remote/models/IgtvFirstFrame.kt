package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class IgtvFirstFrame(
    @SerializedName("estimated_scans_sizes") val estimatedScansSizes: List<Int>? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("scans_profile") val scansProfile: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("width") val width: Int? = null
)