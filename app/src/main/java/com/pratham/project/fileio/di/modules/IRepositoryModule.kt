package com.pratham.project.fileio.di.modules

import com.pratham.project.fileio.ui.home.HomeRepositoryImpl
import com.pratham.project.fileio.ui.home.IHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class IRepositoryModule {

    @Singleton
    @Binds
    abstract fun providesIHomeRepository(repository: HomeRepositoryImpl): IHomeRepository

}