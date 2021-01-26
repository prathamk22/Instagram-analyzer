package com.pratham.project.fileio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Files(
    @PrimaryKey val int: Int,
    val name: String
)
