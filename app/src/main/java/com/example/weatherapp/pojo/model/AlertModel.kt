package com.example.weatherapp.pojo.model

import com.example.weatherapp.pojo.model.dbentities.AlertEntity
import com.example.weatherapp.ui.alert.view.AlertAdapter

data class AlertModel(
    var list: ArrayList<AlertEntity>?,
    var listener : AlertAdapter.AlertAdapterListener
    )
