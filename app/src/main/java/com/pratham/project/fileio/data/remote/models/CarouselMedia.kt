package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class CarouselMedia(
    @SerializedName("can_see_insights_as_brand") val canSeeInsightsAsBrand: Boolean? = null,
    @SerializedName("carousel_parent_id") val carouselParentId: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("image_versions2") val imageVersions2: ImageVersions2? = null,
    @SerializedName("media_type") val mediaType: Int? = null,
    @SerializedName("original_height") val originalHeight: Int? = null,
    @SerializedName("original_width") val originalWidth: Int? = null,
    @SerializedName("pk") val pk: Long? = null,
    @SerializedName("sharing_friction_info") val sharingFrictionInfo: SharingFrictionInfo? = null,
    @SerializedName("usertags") val usertags: Usertags? = null
)