package com.example.weatherapp.util

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.ui.home.DayAdapter

@BindingAdapter("getWeatherIcon")
fun ImageView.getWeatherIcon(icon: String) {
    this.setImageDrawable(getDrawableIcon(context, icon))
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setTempDegree")
fun TextView.setTempDegree(degree: Double) {
    val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    val isKelvin = pref.getBoolean(context.getString(R.string.is_kelvin), true)
    this.text = "${degree.toInt()}${context.getString(R.string.degree_symble)}"
    if (isKelvin) this.append("K") else this.append("C")
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setWindSpeed")
fun TextView.setWindSpeed(windSpeed: Double) {
    val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    val windSpeedUnit = pref.getBoolean(context.getString(R.string.wind_speed_unit), true)
    if (windSpeedUnit)
        this.text = "${windSpeed.toInt()}M/S"
    else
        this.text = "${windSpeed.toInt()}M/H"

}

@BindingAdapter("homeScreenAdapter")
fun RecyclerView.homeScreenAdapter(list: ArrayList<Hourly>) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    val dayAdapter = DayAdapter(context)
    dayAdapter.setList(list)
    this.adapter = dayAdapter
}

