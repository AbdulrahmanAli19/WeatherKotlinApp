package abdulrahman.ali19.kist.pojo.model

import abdulrahman.ali19.kist.pojo.model.weather.Temp

data class WeekModel(
    var date: Long,
    var temp: Temp,
    var icon: String
)
