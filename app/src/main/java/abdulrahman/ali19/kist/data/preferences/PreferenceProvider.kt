package abdulrahman.ali19.kist.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng

private const val PREF_NAME = "myAppPref"
private const val TEMP_UNIT_KEY = "tempUnit"
private const val WIND_SPEED_KEY = "windSpeedUnit"
private const val LAST_TIMESTAMP = "lastTimeStamp"
private const val DEF_LANG = "defLang"
private const val LAT_KEY = "myLat"
private const val LON_KEY = "myLon"
const val NULL_LAT = 30.02401127333763
const val NULL_LON = 31.564412713050846

class PreferenceProvider(
    context: Context
) : PreferenceInterface {

    private val appContext: Context = context.applicationContext

    private val preference: SharedPreferences =
        appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun getWindSpeedUnit(): String =
        preference.getString(WIND_SPEED_KEY, AppUnits.METER_BY_SECOND.string)
            ?: AppUnits.METER_BY_SECOND.string

    override fun setWindSpeedUnit(windSpeedUnit: String) =
        preference.edit().putString(WIND_SPEED_KEY, windSpeedUnit).apply()

    override fun getLanguage(): String {
        return preference.getString(DEF_LANG, AppUnits.EN.toString()).toString()
    }

    override fun setLanguage(string: String) {
        preference.edit().putString(DEF_LANG, string).apply()
    }

    override fun getTimestamp(): Long? = preference.getString(LAST_TIMESTAMP, null)?.toLong()

    override fun setTimestamp(timestamp: Long) =
        preference.edit().putString(LAST_TIMESTAMP, timestamp.toString()).apply()

    override fun getLatLon(): LatLng =
        LatLng(
            preference.getString(LAT_KEY, null)?.toDouble() ?: NULL_LAT,
            preference.getString(LON_KEY, null)?.toDouble() ?: NULL_LON
        )

    override fun setLatLon(latLng: LatLng) =
        preference.edit().putString(LAT_KEY, latLng.latitude.toString())
            .putString(LON_KEY, latLng.longitude.toString()).apply()

    override fun setTempUnit(tempUnit: String) =
        preference.edit().putString(TEMP_UNIT_KEY, tempUnit).apply()

    override fun getTempUnit(): String =
        preference.getString(TEMP_UNIT_KEY, AppUnits.KELVIN.string) ?: AppUnits.KELVIN.string

}