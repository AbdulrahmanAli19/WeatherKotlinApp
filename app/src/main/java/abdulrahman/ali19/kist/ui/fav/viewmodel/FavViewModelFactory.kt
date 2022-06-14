package abdulrahman.ali19.kist.ui.fav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface

class FavViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}