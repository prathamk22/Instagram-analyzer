package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address") val address: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("external_source") val externalSource: String? = null,
    @SerializedName("facebook_places_id") val facebookPlacesId: Long? = null,
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lng") val lng: Double? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("pk") val pk: Long? = null,
    @SerializedName("short_name") val shortName: String? = null
)