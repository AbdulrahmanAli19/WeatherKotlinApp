package abdulrahman.ali19.kist.data.pojo.model

import abdulrahman.ali19.kist.data.pojo.model.weather.Daily
import abdulrahman.ali19.kist.data.pojo.model.weather.Hourly

data class ReportModel(
    val week: ArrayList<Daily>,
    val day: ArrayList<Hourly>
)