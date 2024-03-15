package dog.beepboop.velvet.controlServer.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UIActionUpdate(
    val useTime: Long,
    val category: String,
    val command: String
)
