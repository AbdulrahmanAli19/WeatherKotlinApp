package abdulrahman.ali19.kist.pojo.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Minutely(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("precipitation")
    val precipitation: Int
) : Serializable