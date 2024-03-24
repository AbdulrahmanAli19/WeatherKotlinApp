package abdulrahman.ali19.kist.di

import abdulrahman.ali19.kist.data.db.AppDatabase
import abdulrahman.ali19.kist.data.local.LocalSource
import abdulrahman.ali19.kist.data.local.LocalSourceImpl
import abdulrahman.ali19.kist.data.pojo.repo.Repository
import abdulrahman.ali19.kist.data.pojo.repo.RepositoryInterface
import abdulrahman.ali19.kist.data.preferences.PreferenceInterface
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.data.remote.RemoteSource
import abdulrahman.ali19.kist.ui.alert.viewmodel.AlertViewModel
import abdulrahman.ali19.kist.ui.favorites.viewmodel.FavoritesViewModel
import abdulrahman.ali19.kist.ui.home.viewmodel.HomeViewModel
import abdulrahman.ali19.kist.ui.map.viewmodel.MapViewModel
import abdulrahman.ali19.kist.ui.settings.viewmodel.SettingsViewModel
import abdulrahman.ali19.kist.ui.splash.viewmodel.SplashViewModel
import abdulrahman.ali19.kist.worker.CreateNotification
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherModules = module {
    single<RepositoryInterface> {
        Repository(
            remoteSource = get(),
            localSource = get(),
            preferences = get()
        )
    }

    single<PreferenceInterface> {
        PreferenceProvider(androidContext())
    }

    single<RemoteSource> {
        ConnectionProvider
    }

    single<LocalSource> {
        LocalSourceImpl(get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "WEATHER_APP_DB"
        ).allowMainThreadQueries().build()
    }

    factory {
        CreateNotification(androidContext())
    }

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        AlertViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        MapViewModel(get())
    }

    viewModel {
        SettingsViewModel(get())
    }
}