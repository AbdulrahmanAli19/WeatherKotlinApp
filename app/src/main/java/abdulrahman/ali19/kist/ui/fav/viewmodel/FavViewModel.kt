package abdulrahman.ali19.kist.ui.fav.viewmodel


import abdulrahman.ali19.kist.data.remote.Resource
import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavViewModel.dev"

class FavViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

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