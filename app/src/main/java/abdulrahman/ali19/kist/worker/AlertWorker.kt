package abdulrahman.ali19.kist.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.pojo.repo.Repository
import kotlinx.coroutines.*

private const val TAG = "AlertWorker"
class AlertWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val alertScope = CoroutineScope(Dispatchers.IO + Job())

    override fun doWork(): Result {

        val repo = Repository.getInstance(
            localSource = ConcreteLocalSource.getInstance(context),
            preferences = PreferenceProvider(context)
        )

        alertScope.launch {
            Log.d(TAG, "doWork: called")
            val data = repo.getWeatherByLatLon(repo.getLatLon(), repo.getLanguage())
            if (data.alerts.isEmpty()) {
                CreateNotification(context).createNotification(
                    context.getString(R.string.weather_is)+ data.current.weather[0].description,
                    context.getString(R.string.thereisnoaerts)
                )
            } else {
                CreateNotification(context).createNotification(
                    "Pleas check the application",
                    "Weather Alerts!!"
                )
            }
        }

        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        alertScope.cancel()
    }
}