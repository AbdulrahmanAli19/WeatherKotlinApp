package abdulrahman.ali19.kist.data.remote

import abdulrahman.ali19.kist.data.preferences.AppUnits
import abdulrahman.ali19.kist.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.util.MY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = AppUnits.EN.string,
        @Query("appid") appid: String = MY_API_KEY
    ): WeatherResponse
}