package abdulrahman.ali19.kist.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object AddAlertRemainder {

    fun addPeriodicAlert(time: Long = 12, delay: Long, context: Context, workerTag: String= "0") {

        val data = Data.Builder()
            .putLong("delay", delay)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(AlertPeriodicWorker::class.java, time, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("P$workerTag")
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }

    fun addSingleAlert(time: Long, context: Context, workerTag : String = "0") {
        val delay: Long = time / 1000L - System.currentTimeMillis() / 1000L

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(AlertWorker::class.java)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .addTag(workerTag)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }

    fun removeWorkers(workerTag: String, context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(workerTag)
        WorkManager.getInstance(context).cancelAllWorkByTag("P$workerTag")
    }

}