package abdulrahman.ali19.kist.data.remote

import abdulrahman.ali19.kist.data.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.util.BASE_URL
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ConnectionProvider : RemoteSource {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()

    private fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    override suspend fun getWeatherByLatAndLing(
        latLng: LatLng,
        language: String
    ): WeatherResponse =
        weatherApi.getWeatherData(
            lat = latLng.latitude.toString(),
            lon = latLng.longitude.toString(),
            lang = language
        )

}