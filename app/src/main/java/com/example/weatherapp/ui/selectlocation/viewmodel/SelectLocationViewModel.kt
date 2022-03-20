package com.example.weatherapp.ui.selectlocation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.repo.RepositoryInterface
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectLocationViewModel(private val repository: RepositoryInterface) : ViewModel() {

    fun saveMyLatLon(latlon: LatLng) = repository.setLatLon(latlon)

    fun getMyLatLon() = repository.getLatLon()

    fun addFavoriteTodatabase(fav: FavoriteEntity) {
        GlobalScope.launch {
            repository.insertFavorite(fav)
        }
    }

}