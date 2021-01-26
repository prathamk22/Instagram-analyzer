package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class In(
    @SerializedName("duration_in_video_in_sec") val durationInVideoInSec: Any? = null,
    @SerializedName("position") val position: List<Double>? = null,
    @SerializedName("start_time_in_video_in_sec") val startTimeInVideoInSec: Any? = null,
    @SerializedName("user") val user: UserXXXX? = null
)