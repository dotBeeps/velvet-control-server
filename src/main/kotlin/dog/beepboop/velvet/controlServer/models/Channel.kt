package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "channels")
data class Channel (
    @DynamoDBHashKey(attributeName = "channel_id")
    var channelId: String = "",
    @DynamoDBAttribute(attributeName = "channel_name")
    var channelName: String = "",
    @DynamoDBAttribute
    var active: Boolean = false,
    @DynamoDBAttribute(attributeName = "bits_enabled")
    var bitsEnabled: Boolean = false
)
