package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class SharingFrictionInfoX(
    @SerializedName("bloks_app_url") val bloksAppUrl: Any? = null,
    @SerializedName("should_have_sharing_friction") val shouldHaveSharingFriction: Boolean? = null
)