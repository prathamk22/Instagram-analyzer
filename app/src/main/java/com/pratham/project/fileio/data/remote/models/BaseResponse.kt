package com.pratham.project.fileio.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("message") @Expose val message: String? = null,
    @SerializedName("error_title") @Expose val errorTitle: String? = null,
    @SerializedName("error_body") @Expose val errorBody: String? = null,
    @SerializedName("logout_reason") @Expose val logoutReason: String? = null,
    @SerializedName("status") @Expose val status: String? = null
){}