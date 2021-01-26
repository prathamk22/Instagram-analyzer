package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class AudioReattributionInfo(
    @SerializedName("should_allow_restore") val shouldAllowRestore: Boolean? = null
)