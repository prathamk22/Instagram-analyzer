package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class ImageVersions2(
    @SerializedName("additional_candidates") val additionalCandidates: AdditionalCandidates? = null,
    @SerializedName("candidates") val candidates: List<Candidate>? = null
)