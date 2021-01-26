package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsernameInfo(
        @SerializedName("status") @Expose val status: String? = null,
        @SerializedName("user") @Expose val user: User? = null
)