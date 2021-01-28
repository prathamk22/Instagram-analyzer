package com.pratham.project.fileio.data.remote.models

import com.google.gson.annotations.SerializedName

data class FollowersModel(
    @SerializedName("big_list") val bigList: Boolean? = null,
    @SerializedName("global_blacklist_sample") val globalBlacklistSample: Any? = null,
    @SerializedName("next_max_id") val nextMaxId: String? = null,
    @SerializedName("page_size") val pageSize: Int? = null,
    @SerializedName("sections") val sections: Any? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("users") val users: List<UserXX>? = null
)