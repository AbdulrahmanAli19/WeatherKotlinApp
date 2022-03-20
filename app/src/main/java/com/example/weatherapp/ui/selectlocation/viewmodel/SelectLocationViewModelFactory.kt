package com.example.weatherapp.ui.selectlocation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.pojo.repo.RepositoryInterface

class SelectLocationViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectLocationViewModel::class.java)) {
            return SelectLocationViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}