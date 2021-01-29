package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class LikesModel(
    @SerializedName("auto_load_more_enabled") val autoLoadMoreEnabled: Boolean? = null,
    @SerializedName("items") val items: List<Item>? = null,
    @SerializedName("more_available") val moreAvailable: Boolean? = null,
    @SerializedName("next_max_id") val nextMaxId: String? = null,
    @SerializedName("num_results") val numResults: Int? = null,
    @SerializedName("status") val status: String? = null
)