package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped
import kotlinx.serialization.Serializable
import java.util.Date

@DynamoDBTable(tableName = "actions")
data class Action(@DynamoDBHashKey
    var id: String = "",
    @DynamoDBAttribute
    var channelId: String = "",
    @DynamoDBAttribute
    var category: String = "",
    @DynamoDBAttribute
    var name: String = "",
    @DynamoDBAttribute
    var command: String = "",
    @DynamoDBAttribute
    var cooldown: Int = 0,
    @DynamoDBAttribute
    var script: Script = Script(),
    @DynamoDBAttribute(attributeName = "last_use")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    var lastUse: Date = Date(0)
)

@Serializable
data class Script (
    @DynamoDBAttribute
    var command: String = "",
    @DynamoDBAttribute
    var parameters: Map<String, Int> = mapOf()
)
data class ActionResponse (
    val name: String,
    val category: String,
    val command: String,
    val cooldown: Int,
    val lastUse: Date
)