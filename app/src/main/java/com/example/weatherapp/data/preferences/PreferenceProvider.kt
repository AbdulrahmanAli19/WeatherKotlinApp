package com.example.weatherapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng

private const val PREF_NAME = "myAppPref"
private const val TEMP_UNIT_KEY = "tempUnit"
private const val WIND_SPEED_KEY = "windSpeedUnit"
private const val LAST_TIMESTAMP = "lastTimeStamp"
private const val LAT_KEY = "myLat"
private const val LON_KEY = "myLon"
const val NULL_LAT_LON = 0.000001

class PreferenceProvider(
    context: Context
) {

    private val appContext: Context = context.applicationContext

    private val preference: SharedPreferences =
        appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveTempUnit(string: String) =
        preference.edit().putString(TEMP_UNIT_KEY, string).apply()

    fun saveWindSpeedUnit(string: String) =
        preference.edit().putString(WIND_SPEED_KEY, string).apply()

    fun saveLastTimeStamp(string: String) =
        preference.edit().putString(LAST_TIMESTAMP, string).apply()

    fun saveMyLatLon(latLng: LatLng) =
        preference.edit().putString(LAT_KEY, latLng.latitude.toString())
            .putString(LON_KEY, latLng.longitude.toString()).apply()

    fun getMyLatLon(): LatLng =
        LatLng(
            preference.getString(LAT_KEY, null)?.toDouble() ?: NULL_LAT_LON,
            preference.getString(LON_KEY, null)?.toDouble() ?: NULL_LAT_LON
        )

    fun getLastTimeStamp(): Double? = preference.getString(LAST_TIMESTAMP, null)?.toDouble()

    fun getWindSpeedUnit(): String? = preference.getString(WIND_SPEED_KEY, null)

    fun getTempUnit(): String? = preference.getString(TEMP_UNIT_KEY, null)

}