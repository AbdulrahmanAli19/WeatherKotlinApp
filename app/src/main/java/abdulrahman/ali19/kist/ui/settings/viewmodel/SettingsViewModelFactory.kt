package abdulrahman.ali19.kist.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface

class SettingsViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}