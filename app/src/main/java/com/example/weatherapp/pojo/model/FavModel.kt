package com.example.weatherapp.pojo.model

import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.ui.fav.view.FavAdapter

data class FavModel(
    val favAdapterInterface: FavAdapter.FavAdapterInterface,
    val countries: List<FavoriteEntity>
)
