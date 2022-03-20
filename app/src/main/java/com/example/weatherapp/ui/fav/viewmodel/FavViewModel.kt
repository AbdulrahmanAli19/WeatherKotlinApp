package com.example.weatherapp.ui.fav.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "FavViewModel.dev"

class FavViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun getFavoriteList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repositoryInterface.getAllFavorites()))
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

    fun deleteFromFavorite(favoriteEntity: FavoriteEntity) {
        GlobalScope.launch {
            repositoryInterface.deleteFavorite(favoriteEntity)
        }
    }
}