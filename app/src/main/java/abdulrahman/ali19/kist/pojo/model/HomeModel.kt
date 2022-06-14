package abdulrahman.ali19.kist.pojo.model

import abdulrahman.ali19.kist.pojo.model.weather.Hourly

data class HomeModel(
    var city: String,
    var date: String,
    var icon: String,
    var temp: Double,
    var desc: String,
    var wind: Double,
    var humidity: Int,
    var feelsLike: Double,
    var dayList : ArrayList<Hourly>
)