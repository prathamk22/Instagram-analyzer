package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class MashupInfo(
    @SerializedName("can_toggle_mashups_allowed") val canToggleMashupsAllowed: Boolean? = null,
    @SerializedName("has_been_mashed_up") val hasBeenMashedUp: Boolean? = null,
    @SerializedName("mashups_allowed") val mashupsAllowed: Boolean? = null,
    @SerializedName("original_media") val originalMedia: Any? = null
)