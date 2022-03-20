package com.example.weatherapp.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.pojo.repo.RepositoryInterface

class SettingsViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun setTempUnit(string: String) = repositoryInterface.setTempUnit(string)

    fun getTempUnit() = repositoryInterface.getTempUnit()

    fun setWindSpeedUnit(string: String) = repositoryInterface.setWindSpeedUnit(string)

    fun getWindSpeedUnit() = repositoryInterface.getWindSpeedUnit()

    fun getLanguage() = repositoryInterface.getLanguage()

    fun setLanguage (string: String) = repositoryInterface.setLanguage(string)

}