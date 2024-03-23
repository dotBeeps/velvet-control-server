package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import java.io.Serializable

@DynamoDBDocument
data class CommandEntryId(
    @field:DynamoDBHashKey(attributeName = "channel_id")
    var channelId: Int = 0,
    @field:DynamoDBRangeKey(attributeName = "command_id")
    var commandId: String? = ""
) : Serializable
