package com.pratham.project.fileio.di

import android.app.Application
import androidx.room.Room
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.AppDatabase
import com.pratham.project.fileio.data.remote.InstagramAPI
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.ui.home.HomeRepository
import com.pratham.project.fileio.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }

    single { HomeRepository(get(), get(), get(), get(), get(), get()) }
}

val instagramApiModule = module {

    single { InstagramAPI(androidApplication()) }

    single {
        val instaApi: InstagramAPI = get()
        instaApi.api
    }

}

val preferencesModule = module {
    single { provideSettingsPreferences(androidApplication()) }
}

fun provideSettingsPreferences(app: Application): PreferenceManager = PreferenceManager(app)

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "instagram-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory {
        val database: AppDatabase = get()
        database.getUsernameDao()
    }

    factory {
        val database: AppDatabase = get()
        database.getFollowersDao()
    }

    factory {
        val database: AppDatabase = get()
        database.getFollowingDao()
    }

    factory {
        val database: AppDatabase = get()
        database.getUserLocalDao()
    }

}
