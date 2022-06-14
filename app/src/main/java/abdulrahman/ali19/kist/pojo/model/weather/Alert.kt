package abdulrahman.ali19.kist.pojo.model.weather

import java.io.Serializable

data class Alert(
    val description: String,
    val end: Long,
    val event: String,
    val sender_name: String,
    val start: Long,
    val tags: List<String>
) : Serializable