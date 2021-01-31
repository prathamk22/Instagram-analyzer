package com.pratham.project.fileio.di.modules

import android.content.Context
import com.pratham.project.fileio.data.remote.InstagramAPI
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesInstagramAPI(
        @ApplicationContext context: Context
    ): InstagramAPI{
        return InstagramAPI(context)
    }

    @Provides
    @Singleton
    fun providesInstagramAPICalls(
        instagramAPI: InstagramAPI
    ): InstagramAPICalls{
        return instagramAPI.api
    }

}