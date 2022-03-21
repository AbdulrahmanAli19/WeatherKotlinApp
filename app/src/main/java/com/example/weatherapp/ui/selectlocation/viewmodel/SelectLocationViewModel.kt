package com.example.weatherapp.ui.selectlocation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.preferences.AppUnits
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.repo.RepositoryInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SelectLocationViewModel"

class SelectLocationViewModel(private val repository: RepositoryInterface) : ViewModel() {

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
        Log.d(TAG, "getDataFromRepo: called and its loading ")
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repository.getWeatherByLatLon(latLng, language)))
            Log.d(TAG, "getDataFromRepo: scs")
        } catch (exception: Exception) {
            Log.d(TAG, "getDataFromRepo: Exception ${exception.message}")
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        }
    }

    fun getLang() = repository.getLanguage()

}