package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class AdditionalAudioInfo(
    @SerializedName("additional_audio_username") val additionalAudioUsername: Any? = null,
    @SerializedName("audio_reattribution_info") val audioReattributionInfo: AudioReattributionInfo? = null
)