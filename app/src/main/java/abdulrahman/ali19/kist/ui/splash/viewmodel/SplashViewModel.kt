package abdulrahman.ali19.kist.ui.splash.viewmodel

import abdulrahman.ali19.kist.data.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.data.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.data.pojo.repo.RepositoryInterface
import abdulrahman.ali19.kist.data.preferences.AppUnits
import abdulrahman.ali19.kist.data.remote.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "SplashViewModel"

class SplashViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun getDataFromRepo(
        latLng: LatLng = LatLng(
            30.02401127333763,
            31.564412713050846
        ),
        language: String = AppUnits.EN.string
    ) = liveData(Dispatchers.IO) {
        Log.d(TAG, "getDataFromRepo: called and its loading ")
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repository.getWeatherByLatLon(latLng, language)))
            Log.d(TAG, "getDataFromRepo: scs")
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    exception.message ?: "Are you connected !",
                )
            )
        }
    }

    fun saveResponse(weatherResponse: WeatherResponse) {
        viewModelScope.launch {
            repository.insertCashed(
                CashedEntity(
                    cashedData = weatherResponse
                )
            )
        }
    }

    fun getLatLon() = repository.getLatLon()

    fun setTimeStamp(msTime: Long) = repository.setTimestamp(msTime)

    fun setLatLon(latLng: LatLng) = repository.setLatLon(latLng)

    fun getLang() = repository.getLanguage()

}