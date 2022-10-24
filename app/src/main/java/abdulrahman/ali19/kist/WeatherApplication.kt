package abdulrahman.ali19.kist

import abdulrahman.ali19.kist.di.weatherModules
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            modules(weatherModules)
            androidContext(this@WeatherApplication)
        }
    }
}