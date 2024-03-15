package dog.beepboop.velvet.controlServer.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PubSubMessage(@SerialName("content_type") val contentType: String, val message: String, val targets: Array<String>)