package abdulrahman.ali19.kist.ui.favorites.viewmodel


import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.data.pojo.repo.RepositoryInterface
import abdulrahman.ali19.kist.data.remote.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavViewModel.dev"

class FavoritesViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun getFavoriteList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repositoryInterface.getAllFavorites()))
        } catch (exception: Exception) {
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        }
    }

    fun deleteFromFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repositoryInterface.deleteFavorite(favoriteEntity)
        }
    }
}