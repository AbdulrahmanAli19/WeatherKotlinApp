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
import com.example.weatherapp.pojo.model.weather.Daily
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.pojo.model.weather.Temp
import com.example.weatherapp.ui.home.DayAdapter
import com.example.weatherapp.ui.weekreport.WeekAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@BindingAdapter("setWeatherIcon")
fun ImageView.setWeatherIcon(icon: String) {
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

@BindingAdapter("setDayAdapter")
fun RecyclerView.setDayAdapter(list: ArrayList<Hourly>?) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    val dayAdapter = DayAdapter(context)
    dayAdapter.setList(list ?: arrayListOf())
    this.adapter = dayAdapter
}

@BindingAdapter("setWeekAdapter")
fun RecyclerView.setWeekAdapter(list: ArrayList<Daily>?) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    val weekAdapter = WeekAdapter()
    weekAdapter.setWeek(list ?: arrayListOf())
    this.adapter = weekAdapter
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setWeekTxt")
fun TextView.setWeekTxt(msDate: Long) {
    val date = Date(msDate * 1000L)
    val format = SimpleDateFormat("EEE")
    this.text = (format.format(date))
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setDayAndMonthDate")
fun TextView.setDayAndMonthDate(msDate: Long) {
    val date = Date(msDate * 1000L)
    val format = SimpleDateFormat("dd MMM")
    this.text = (format.format(date))
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setMinAndMaxDegree")
fun TextView.setMinAndMaxDegree(temp: Temp) {
    val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    val isKelvin = pref.getBoolean(context.getString(R.string.is_kelvin), true)
    this.text =
        "${temp.max.toInt()}/${temp.min.toInt()}${context.getString(R.string.degree_symble)}"
    if (isKelvin) this.append("K") else this.append("C")
}





















