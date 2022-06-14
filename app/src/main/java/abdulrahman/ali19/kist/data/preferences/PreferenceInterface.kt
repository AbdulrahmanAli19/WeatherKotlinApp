package abdulrahman.ali19.kist.data.preferences

import com.google.android.gms.maps.model.LatLng

interface PreferenceInterface {

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