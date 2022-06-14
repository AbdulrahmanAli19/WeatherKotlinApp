package abdulrahman.ali19.kist.ui.alert.viewmodel

import abdulrahman.ali19.kist.data.remote.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.pojo.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertViewModel(val repositoryInterface: RepositoryInterface) : ViewModel() {

    fun insertAlert(alertEntity: AlertEntity) {
        viewModelScope.launch {
            repositoryInterface.insertAlert(alertEntity)
        }
    }

    fun getAllAlerts() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(isLoading = true, _data = null))
        try {
            emit(Resource.Success(_data = repositoryInterface.getAllAlerts()))
        } catch (exception: Exception) {
            Resource.Error(exception = exception.message ?: "Error")
        }
    }

    fun removeAlert(alertEntity: AlertEntity) {
        viewModelScope.launch { repositoryInterface.deleteAlert(alertEntity) }
    }

}