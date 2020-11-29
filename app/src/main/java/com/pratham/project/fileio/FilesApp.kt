package com.pratham.project.fileio

import android.app.Application
import com.pratham.project.fileio.di.databaseModule
import com.pratham.project.fileio.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FilesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FilesApp)
            modules(listOf(viewModelModule, databaseModule))
        }
    }
}
