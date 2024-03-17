package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "users")
data class User (
    @DynamoDBAttribute(attributeName = "channel_name")
    var channelName: String = "",
    @DynamoDBHashKey(attributeName = "channel_id")
    var channelId: String = "",
    @DynamoDBAttribute(attributeName = "control_enabled")
    var enabled: Boolean = false
)
