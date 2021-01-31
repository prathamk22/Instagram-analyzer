package com.pratham.project.fileio.di.modules

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.dao.*
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.ui.home.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesHomeRepositoryImpl(
        instagramAPICalls: InstagramAPICalls,
        preferenceManager: PreferenceManager,
        usernameDao: UsernameDao,
        followersDao: FollowersDao,
        followingsDao: FollowingsDao,
        userLocalCountsDao: UserLocalCountsDao,
        feedsDao: FeedsDao
    ): HomeRepositoryImpl {
        return HomeRepositoryImpl(
            instagramAPICalls,
            preferenceManager,
            usernameDao,
            followersDao,
            followingsDao,
            userLocalCountsDao,
            feedsDao
        )
    }

}