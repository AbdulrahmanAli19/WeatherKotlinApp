package abdulrahman.ali19.kist.pojo.repo

import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.pojo.model.weather.WeatherResponse
import com.google.android.gms.maps.model.LatLng

interface RepositoryInterface {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    suspend fun updateFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getAllFavorites(): List<FavoriteEntity>

    suspend fun insertCashed(cashedEntity: CashedEntity)

    suspend fun deleteCashed(cashedEntity: CashedEntity)

    suspend fun updateCashed(cashedEntity: CashedEntity)

    suspend fun getAllCashed(): List<CashedEntity>

    suspend fun getWeatherByLatLon(latLng: LatLng, language : String): WeatherResponse

    suspend fun insertAlert(alertEntity: AlertEntity)

    suspend fun deleteAlert(alertEntity: AlertEntity)

    suspend fun updateAlert(alertEntity: AlertEntity)

    suspend fun getAllAlerts() : List<AlertEntity>

    fun getTimestamp(): Long?

    fun setTimestamp(timestamp: Long)

    fun getLatLon(): LatLng

    fun setLatLon(latLng: LatLng)

    fun setTempUnit(tempUnit: String)

    fun getTempUnit(): String

    fun getWindSpeedUnit(): String

    fun setWindSpeedUnit(windSpeedUnit: String)

    fun getLanguage(): String

    fun setLanguage(string: String)
}