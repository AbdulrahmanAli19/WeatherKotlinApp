package com.example.weatherapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UseCompatLoadingForDrawables")
fun getDrawableIcon(context: Context, icon: String): Drawable? {
    return when (icon) {
        "01d" -> context.getDrawable(R.drawable.mostly_sunny)
        "01n" -> context.getDrawable(R.drawable.clear_night)
        "02d" -> context.getDrawable(R.drawable.party_cloudy)
        "02n" -> context.getDrawable(R.drawable.party_cloudy_night)
        "03d" -> context.getDrawable(R.drawable.mostly_cloudy)
        "03n" -> context.getDrawable(R.drawable.mostly_cloudy)
        "04d" -> context.getDrawable(R.drawable.mostly_cloudy)
        "04n" -> context.getDrawable(R.drawable.mostly_cloudy)
        "09n" -> context.getDrawable(R.drawable.rain)
        "09d" -> context.getDrawable(R.drawable.rain)
        "10d" -> context.getDrawable(R.drawable.scattered_showers)
        "10n" -> context.getDrawable(R.drawable.scattered_showers_night)
        "11d" -> context.getDrawable(R.drawable.scattered_thunderstorm)
        "11n" -> context.getDrawable(R.drawable.scattered_thunderstorm_night)
        "13d" -> context.getDrawable(R.drawable.snow)
        "13n" -> context.getDrawable(R.drawable.snow_night)
        "50d" -> context.getDrawable(R.drawable.tornado)
        "50n" -> context.getDrawable(R.drawable.tornado)
        else -> null
    }
}

fun getIntIcon(icon: String): Int? {
    return when (icon) {
        "01d" -> (R.drawable.mostly_sunny)
        "01n" -> (R.drawable.clear_night)
        "02d" -> (R.drawable.party_cloudy)
        "02n" -> (R.drawable.party_cloudy_night)
        "03d" -> (R.drawable.mostly_cloudy)
        "03n" -> (R.drawable.mostly_cloudy)
        "04d" -> (R.drawable.mostly_cloudy)
        "04n" -> (R.drawable.mostly_cloudy)
        "09n" -> (R.drawable.rain)
        "09d" -> (R.drawable.rain)
        "10d" -> (R.drawable.scattered_showers)
        "10n" -> (R.drawable.scattered_showers_night)
        "11d" -> (R.drawable.scattered_thunderstorm)
        "11n" -> (R.drawable.scattered_thunderstorm_night)
        "13d" -> (R.drawable.snow)
        "13n" -> (R.drawable.snow_night)
        "50d" -> (R.drawable.tornado)
        "50n" -> (R.drawable.tornado)
        else -> null
    }
}

@SuppressLint("SimpleDateFormat")
fun getTime(msDate: Long): String {
    val date = Date(msDate * 1000L)
    val format = SimpleDateFormat("HH:mm a")
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDate(msDate: Long): String {
    val date = Date(msDate * 1000L)
    val format = SimpleDateFormat("dd MMM,yyyy")
    return format.format(date)
}

fun isNetworkConnected(context: Context): Boolean {
    val connection: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
    val networkInfo: NetworkInfo? = connection.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun fromKelvinToCelsius(degree: Double): Double {
    return degree - 273.15
}

fun fromKelvinToFahrenheit(degree: Double): Double {
    return 1.8 * (degree - 273) + 32
}


fun fromMeterBySecToMileByHour(speed: Double): Double {
    return speed * 2.236936;
}

