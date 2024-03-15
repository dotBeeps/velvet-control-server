package dog.beepboop.velvet.controlServer.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.Date

@Document("actions")
data class Action (
    @Id
    val id: ObjectId = ObjectId(),
    @Field("channel_id")
    val channelId: String = "",
    val category: String = "",
    val name: String = "",
    val command: String = "",
    val cooldown: Int = 0,
    val script: Script,
    @Field("last_use")
    val lastUse: Date = Date(0)
)

@Serializable
data class Script (
    @Field("Command")
    val command: String = "",
    @Field("Parameters")
    val parameters: Map<String, Int>
)

data class ActionResponse (
    val name: String,
    val category: String,
    val command: String,
    val cooldown: Int,
    @Field("last_use")
    val lastUse: Date
)