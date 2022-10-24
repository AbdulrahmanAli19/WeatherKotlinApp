package abdulrahman.ali19.kist.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import abdulrahman.ali19.kist.R
import androidx.core.app.NotificationManagerCompat

private const val TAG = "CreateNotification"

class CreateNotification(private val context: Context) {

    private val CHANNEL_ID = "ALERT_REMINDER_CHANNEL"

    @SuppressLint("MissingPermission")
    fun createNotification(body: String?, title: String?) {

        val builder: NotificationCompat.Builder = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        with(NotificationManagerCompat.from(context)) {
            notify(123, builder.build())
        }
    }
}