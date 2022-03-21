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
import com.example.weatherapp.pojo.model.FavModel
import com.example.weatherapp.pojo.model.dbentities.AlertEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.model.weather.Daily
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.pojo.model.weather.Temp
import com.example.weatherapp.ui.alert.view.AlertAdapter
import com.example.weatherapp.ui.fav.view.FavAdapter
import com.example.weatherapp.ui.home.view.DayAdapter
import com.example.weatherapp.ui.weekreport.view.WeekAdapter
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

@BindingAdapter("setAlertAdapter")
fun RecyclerView.setAlertAdapter(list: ArrayList<AlertEntity>?) {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    val alertAdapter = AlertAdapter()
    alertAdapter.setList(list ?: arrayListOf())
    this.adapter = alertAdapter
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


@BindingAdapter("setFavoriteAdapter")
fun RecyclerView.setFavoriteAdapter(favModel: FavModel?) {
    if (favModel != null) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val favAdapter = FavAdapter(listener = favModel.favAdapterInterface)
        favAdapter.setCountries(favModel.countries as ArrayList<FavoriteEntity>)
        this.adapter = favAdapter
    }
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("setStartDate")
fun TextView.setStartDate(long: Long) {
    val format = SimpleDateFormat("dd MMM")
    this.text = "From: ${format.format(long)}"
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("setEndDate")
fun TextView.setEndDate(long: Long) {
    val format = SimpleDateFormat("dd MMM")
    this.text = "From: ${format.format(long)}"
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("setStartTime")
fun TextView.setStartTime(long: Long) {
    val format = SimpleDateFormat("hh:mm aaa")
    this.text = "From: ${format.format(long)}"
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("setEndTime")
fun TextView.setEndTime(long: Long) {
    val format = SimpleDateFormat("hh:mm aaa")
    this.text = "From: ${format.format(long)}"
}


















