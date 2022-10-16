package abdulrahman.ali19.kist.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface

class MapViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectLocationViewModel::class.java)) {
            return SelectLocationViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}