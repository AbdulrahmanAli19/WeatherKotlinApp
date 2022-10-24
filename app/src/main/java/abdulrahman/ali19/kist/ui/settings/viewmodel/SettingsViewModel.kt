package abdulrahman.ali19.kist.ui.settings.viewmodel

import abdulrahman.ali19.kist.data.pojo.repo.RepositoryInterface
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun setTempUnit(string: String) = repositoryInterface.setTempUnit(string)

    fun getTempUnit() = repositoryInterface.getTempUnit()

    fun setWindSpeedUnit(string: String) = repositoryInterface.setWindSpeedUnit(string)

    fun getWindSpeedUnit() = repositoryInterface.getWindSpeedUnit()

    fun getLanguage() = repositoryInterface.getLanguage()

    fun setLanguage (string: String) = repositoryInterface.setLanguage(string)

}