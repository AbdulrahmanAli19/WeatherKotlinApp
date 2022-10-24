package abdulrahman.ali19.kist.ui.map.viewmodel

import abdulrahman.ali19.kist.data.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.data.pojo.repo.RepositoryInterface
import abdulrahman.ali19.kist.data.preferences.AppUnits
import abdulrahman.ali19.kist.data.remote.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SelectLocationViewModel"

class MapViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun saveMyLatLon(latlon: LatLng) = repository.setLatLon(latlon)

    fun getMyLatLon() = repository.getLatLon()

    fun updateCashedData(cashedEntity: CashedEntity) =
        viewModelScope.launch { repository.insertCashed(cashedEntity) }


    fun addFavoriteTodatabase(fav: FavoriteEntity) {
        viewModelScope.launch {
            repository.insertFavorite(fav)
        }
    }

    fun getWeatherRemotlyLatlon(
        latLng: LatLng = LatLng(
            30.02401127333763,
            31.564412713050846
        ),
        language: String = AppUnits.EN.string
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repository.getWeatherByLatLon(latLng, language)))
        } catch (exception: Exception) {
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        }
    }

    fun getLang() = repository.getLanguage()

}