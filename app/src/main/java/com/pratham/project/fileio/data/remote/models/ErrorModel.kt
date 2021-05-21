package com.pratham.project.fileio.data.remote.models

data class ErrorModel(
    val errorTitle: String = "Error",
    val errorMsg: String,
    val showDialog: Boolean,
    val errorCode: Int? = 0,
)
