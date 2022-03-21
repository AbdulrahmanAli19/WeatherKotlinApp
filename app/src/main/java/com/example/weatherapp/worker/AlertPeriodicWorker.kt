package com.example.weatherapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "AlertPeriodicWorker"
class AlertPeriodicWorker(val context: Context, val workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        Log.d(TAG, "doWork: called")
        AddAlertRemainder.addSingleAlert(context)
        return Result.success()
    }
}