package com.example.weatherapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.pojo.repo.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "AlertWorker"
class AlertWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        val repo = Repository.getInstance(
            remoteSource = ConnectionProvider,
            localSource = ConcreteLocalSource.getInstance(context),
            preferences = PreferenceProvider(context)
        )

        GlobalScope.launch {
            Log.d(TAG, "doWork: called")
            val data = repo.getWeatherByLatLon(repo.getLatLon(), repo.getLanguage())
            if (data.alerts.isNullOrEmpty()) {
                CreateNitification(context).createNotification(
                    "The weather is  ${data.current.weather[0].description}",
                    "Theres not alerts for today"
                )
            } else {
                CreateNitification(context).createNotification(
                    "Pleas check the application",
                    "Weather Alerts!!"
                )
            }
        }


        return Result.success()
    }
}