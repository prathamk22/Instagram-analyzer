package com.pratham.project.fileio.di.modules

import android.content.Context
import androidx.room.Room
import com.pratham.project.fileio.data.local.AppDatabase
import com.pratham.project.fileio.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "instagram-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesUsernameDao(
        appDatabase: AppDatabase
    ): UsernameDao{
        return appDatabase.getUsernameDao()
    }

    @Provides
    @Singleton
    fun providesFollowersDao(
        appDatabase: AppDatabase
    ): FollowersDao{
        return appDatabase.getFollowersDao()
    }

    @Provides
    @Singleton
    fun providesFollowingDao(
        appDatabase: AppDatabase
    ): FollowingsDao{
        return appDatabase.getFollowingDao()
    }

    @Provides
    @Singleton
    fun providesUserLocalDao(
        appDatabase: AppDatabase
    ): UserLocalCountsDao{
        return appDatabase.getUserLocalDao()
    }

    @Provides
    @Singleton
    fun providesFeedsDao(
        appDatabase: AppDatabase
    ): FeedsDao{
        return appDatabase.getFeedsDao()
    }

}