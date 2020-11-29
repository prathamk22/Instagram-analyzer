package com.pratham.project.fileio.di

import androidx.room.Room
import com.pratham.project.fileio.data.local.AppDatabase
import com.pratham.project.fileio.ui.files.FilesRepository
import com.pratham.project.fileio.ui.files.FilesViewModel
import com.pratham.project.fileio.ui.home.HomeRepository
import com.pratham.project.fileio.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FilesViewModel(get()) }

    single { FilesRepository() }
    single { HomeRepository() }
}

//val preferencesModule = module {
//    single { provideSettingsPreferences(androidApplication()) }
//}
//
//fun provideSettingsPreferences(app: Application): PreferenceHelper = PreferenceHelper.getPrefs(app)

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "files-io-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

//    factory {
//        val database: AppDatabase = get()
//        database.getSemesterDao()
//    }

}
