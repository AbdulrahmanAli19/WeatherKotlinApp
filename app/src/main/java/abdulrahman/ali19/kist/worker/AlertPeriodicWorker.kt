package abdulrahman.ali19.kist.worker

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
        val delay = inputData.getLong("delay", System.currentTimeMillis())
        AddAlertRemainder.addSingleAlert(delay, context)
        return Result.success()
    }
}