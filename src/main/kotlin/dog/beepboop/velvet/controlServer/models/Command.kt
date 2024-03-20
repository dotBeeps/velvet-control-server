package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import java.util.Date

@DynamoDBDocument
data class CommandRepoId(
    @DynamoDBHashKey(attributeName = "action_id")
    var actionId: String,
    @DynamoDBRangeKey(attributeName = "channel_id")
    var channelId: Int = 0
)

@DynamoDBTable(tableName = "commands")
data class Command(
    @Id
    @DynamoDBIgnore
    var id: CommandRepoId = CommandRepoId("",0),
    @DynamoDBAttribute
    var category: String = "",
    @DynamoDBAttribute
    var displayName: String = "",
    @DynamoDBAttribute
    var bitCost: List<Int> = listOf(0),
    @DynamoDBAttribute
    var cooldown: List<Int> = listOf(0),
    @DynamoDBAttribute
    var duration: Int? = null,
    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.M)
    var parameters: Script = Script(),
    @DynamoDBAttribute(attributeName = "last_use")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    var lastUse: Date = Date(0)
) {
    @DynamoDBHashKey(attributeName = "action_id")
    fun getActionId(): String = id.actionId
    fun setActionId(action: String) {
        id.actionId = action
    }
    @DynamoDBRangeKey(attributeName = "channel_id")
    fun getChannelId() = id.channelId
    fun setChannelId(channelId: Int) {
        id.channelId = channelId
    }
}


@Serializable
data class Script(
    @DynamoDBAttribute
    var command: String = "",
    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.M)
    var parameters: Map<String, Int> = mapOf()
)

data class ActionResponse(
    val name: String,
    val category: String,
    val command: String,
    val cooldown: Int,
    val lastUse: Date
)