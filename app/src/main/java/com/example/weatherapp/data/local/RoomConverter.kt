package com.example.weatherapp.data.local

import androidx.room.TypeConverter
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.weather.*
import com.google.android.gms.common.server.FavaDiagnosticsEntity
import com.google.gson.Gson

class RoomConverter {
    @TypeConverter
    fun listHourlyTojson(value: List<Hourly>) = Gson().toJson(value)

    @TypeConverter
    fun listDailyToJson(value: List<Daily>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToHourlyList(value: String) = Gson().fromJson(value, Array<Hourly>::class.java).toList()

    @TypeConverter
    fun jsonToDailyList(value: String) = Gson().fromJson(value, Array<Daily>::class.java).toList()

    @TypeConverter
    fun listWeatherToJson(value: List<Weather>) = Gson().toJson(value)

    @TypeConverter
    fun jsonTolistWeather(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()

    @TypeConverter
    fun  listAlertToJson (value:List<Alert>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToAlertList(value: String?): List<Alert>? {
        value?.let {
            return Gson().fromJson(value, Array<Alert>::class.java)?.toList()
        }
        return emptyList()
    }

    @TypeConverter
    fun favoriteToJson (value : FavaDiagnosticsEntity) = Gson().toJson(value)

    @TypeConverter
    fun jsonToFavorite (value : String) = Gson().fromJson(value, FavaDiagnosticsEntity::class.java)

     @TypeConverter
    fun cashedToJson (value : CashedEntity) = Gson().toJson(value)

    @TypeConverter
    fun jsonToCashed (value : String) = Gson().fromJson(value, CashedEntity::class.java)

    @TypeConverter
    fun weatherResToJson (value : WeatherResponse) = Gson().toJson(value)

    @TypeConverter
    fun jsonToWeatherRes ( value : String) = Gson().fromJson(value, WeatherResponse::class.java)

}