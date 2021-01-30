package com.pratham.project.fileio.data.local.models

import com.pratham.project.fileio.data.remote.models.Location

data class LocationCountModel(
        val location: Location?,
        val count: Int?
)