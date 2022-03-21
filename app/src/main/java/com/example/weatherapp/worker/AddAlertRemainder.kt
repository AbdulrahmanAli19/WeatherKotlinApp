package com.example.weatherapp.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object AddAlertRemainder {

    fun addPeriodicAlert(time: Long, context: Context) {
        val convertedTime: Long = time / 1000L - System.currentTimeMillis() / 1000L

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .setRequiresCharging(false)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(AlertPeriodicWorker::class.java, convertedTime, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }

    fun addSingleAlert( context: Context) {

        //val delay: Long = time / 1000L - System.currentTimeMillis() / 1000L

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .setRequiresCharging(false)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(AlertWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }
}