package com.example.weatherapp.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R

class CreateNitification(private val context: Context) {

    @SuppressLint("ObsoleteSdkInt")
    fun createNotification(body: String?, title: String?) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            "MED_REMINDER_CHANNEL"
        )
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "gfhfg"
            val description = "awds"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("MED_REMINDER_CHANNEL1", name, importance)
            channel.description = description
            builder.setChannelId("MED_REMINDER_CHANNEL1")
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(88, builder.build())
    }
}