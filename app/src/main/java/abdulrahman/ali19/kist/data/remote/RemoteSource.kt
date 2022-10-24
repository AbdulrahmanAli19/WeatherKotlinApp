package abdulrahman.ali19.kist.data.remote

import abdulrahman.ali19.kist.data.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

interface RemoteSource {

    suspend fun getWeatherByLatAndLing(latLng: LatLng, language: String): WeatherResponse
}