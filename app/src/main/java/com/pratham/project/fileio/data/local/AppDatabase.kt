package com.pratham.project.fileio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Files::class],
    exportSchema = true,
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

}