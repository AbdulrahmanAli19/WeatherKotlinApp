package abdulrahman.ali19.kist.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface

class SplashViewModelFactory(private val repositoryInterface: RepositoryInterface) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repositoryInterface) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}