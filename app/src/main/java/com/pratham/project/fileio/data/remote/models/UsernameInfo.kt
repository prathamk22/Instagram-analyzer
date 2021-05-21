package com.pratham.project.fileio.data.remote.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsernameInfo(
        @SerializedName("user") @Expose val user: User? = null
): BaseResponse()