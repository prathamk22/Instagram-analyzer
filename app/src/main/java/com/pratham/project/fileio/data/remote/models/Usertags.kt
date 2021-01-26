package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.SerializedName

data class Usertags(
    @SerializedName("in") val inX: List<In>? = null
)