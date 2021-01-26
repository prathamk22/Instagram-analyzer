package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class BrandedContentTagInfo(
    @SerializedName("can_add_tag") val canAddTag: Boolean? = null
)