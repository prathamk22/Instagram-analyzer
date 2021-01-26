package com.pratham.project.fileio.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedsEntity(
        @PrimaryKey val id: Int
)