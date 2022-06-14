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
}