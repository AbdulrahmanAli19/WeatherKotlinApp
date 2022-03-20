package com.example.weatherapp.ui.alert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.pojo.repo.RepositoryInterface

class AlertViewModelFactory(private val repositoryInterface: RepositoryInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            return AlertViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}