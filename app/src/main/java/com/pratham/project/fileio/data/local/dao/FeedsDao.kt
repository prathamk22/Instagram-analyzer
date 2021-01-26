package com.pratham.project.fileio.data.local.dao

import androidx.room.Dao
import com.pratham.project.fileio.data.local.models.FeedsEntity

@Dao
interface FeedsDao : BaseDao<FeedsEntity> {
}