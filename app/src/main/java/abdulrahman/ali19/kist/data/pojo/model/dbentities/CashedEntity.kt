package abdulrahman.ali19.kist.data.pojo.model.dbentities

import abdulrahman.ali19.kist.data.pojo.model.weather.WeatherResponse
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashed_table")
data class CashedEntity(
    @PrimaryKey
    var id: Int = 0,
    var cashedData: WeatherResponse
)