package abdulrahman.ali19.kist.ui.home.viewmodel

import abdulrahman.ali19.kist.data.remote.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers

private const val TAG = "HomeViewModel"

class HomeViewModel(private val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun getCashedData() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repositoryInterface.getAllCashed()))
            Log.d(TAG, "getDataFromRepo: scs")
        } catch (exception: Exception) {
            Log.d(
                TAG, "getDataFromRepo: Exception ${exception.message}"
            )
            emit(
                Resource.Error(
                    exception.message ?: "SomethingWong happened",
                )
            )
        }
    }
}