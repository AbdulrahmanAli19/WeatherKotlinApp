package abdulrahman.ali19.kist.data.pojo.model

import abdulrahman.ali19.kist.data.pojo.model.weather.Temp

data class WeekModel(
    var date: Long,
    var temp: Temp,
    var icon: String
)
