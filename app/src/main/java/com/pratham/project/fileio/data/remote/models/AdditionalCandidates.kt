package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class AdditionalCandidates(
    @SerializedName("first_frame") val firstFrame: FirstFrame? = null,
    @SerializedName("igtv_first_frame") val igtvFirstFrame: IgtvFirstFrame? = null
)