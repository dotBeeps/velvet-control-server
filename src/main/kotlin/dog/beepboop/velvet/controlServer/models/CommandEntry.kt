package dog.beepboop.velvet.controlServer.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.util.Date
@DynamoDBTable(tableName = "commands")
data class CommandEntry(
    @field:Id
    @DynamoDBIgnore
    var id: CommandEntryId = CommandEntryId()
) {
    @get:DynamoDBHashKey(attributeName = "channel_id")
    var channelId: Int
        get() {
            return id.channelId
        }
        set(channelId: Int) {
            id.channelId = channelId
        }
    @DynamoDBRangeKey(attributeName = "command_id")
    fun getCommandId() = id.commandId
    fun setCommandId(commandId: String) {
        id.commandId = commandId
    }
    @get:DynamoDBAttribute
    var enabled: Boolean = false
    @get:DynamoDBAttribute
    var category: String = ""
    @get:DynamoDBAttribute(attributeName = "display_name")
    var displayName: String = ""
    @get:DynamoDBAttribute(attributeName = "bit_cost")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
    var bitCost: List<Int> = listOf(0)
    @get:DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
    var cooldown: List<Int> = listOf(0)
    @get:DynamoDBAttribute
    var duration: Int? = null
    @get:DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.M)
    var parameters: CommandParameters = CommandParameters()
    @get:DynamoDBAttribute(attributeName = "last_use")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    var lastUse: Date = Date(0)
}

@kotlinx.serialization.Serializable
data class CommandParameters(
    var parameters: Map<String, Int> = mapOf(),
    var command: String = "",
    var duration: Int? = null
)

data class InvokeCommandRequest(
    val transactionId: String?,
    val transactionBearer: String?,
    val bits: Int
)

data class ActionResponse(
    val name: String,
    val category: String,
    val command: String,
    val cooldown: Int,
    val lastUse: Date
)