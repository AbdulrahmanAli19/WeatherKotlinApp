package abdulrahman.ali19.kist.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface

class HomeViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}