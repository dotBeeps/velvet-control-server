package dog.beepboop.velvet.controlServer.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("users")
data class User (
    @Id
    val id: ObjectId = ObjectId(),
    @Field("channel_name")
    val channelName: String = "",
    @Field("channel_id")
    val channelId: String = "",
    @Field("control_enabled")
    val enabled: Boolean = false,
)