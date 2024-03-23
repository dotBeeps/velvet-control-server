package dog.beepboop.velvet.controlServer.models

import kotlinx.serialization.Serializable

@Serializable
data class PubSubCommand(
    val commandId: String = "",
    val commandParameters: CommandParameters = CommandParameters(),
    val duration: Int?
)
