package abdulrahman.ali19.kist.data.remote

import abdulrahman.ali19.kist.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.util.BASE_URL
import com.google.android.gms.maps.model.LatLng
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionProvider : RemoteSource {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
    
    override suspend fun getWeatherByLatAndLing(latLng: LatLng, language : String): WeatherResponse =
        weatherApi.getWeatherData(
            lat = latLng.latitude.toString(),
            lon = latLng.longitude.toString(),
            lang = language
        )

}