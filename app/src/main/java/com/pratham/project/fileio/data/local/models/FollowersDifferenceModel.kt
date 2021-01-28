package com.pratham.project.fileio.data.local.models

import com.pratham.project.fileio.data.remote.models.UserXX

data class FollowersDifferenceModel(
        val increaseDifference: Int? = null,
        val decreaseDifference: Int? = null,
        val increaseDiffList: List<UserXX>? = null,
        val decreaseDiffList: List<UserXX>? = null
)