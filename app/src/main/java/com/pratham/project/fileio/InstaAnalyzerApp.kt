package com.pratham.project.fileio

import android.app.Application
import com.pratham.project.fileio.di.databaseModule
import com.pratham.project.fileio.di.instagramApiModule
import com.pratham.project.fileio.di.preferencesModule
import com.pratham.project.fileio.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class InstaAnalyzerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InstaAnalyzerApp)
            modules(listOf(viewModelModule, databaseModule, preferencesModule, instagramApiModule))
        }
    }
}
