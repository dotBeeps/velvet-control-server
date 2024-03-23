package dog.beepboop.velvet.controlServer.services

import dog.beepboop.velvet.controlServer.models.CommandEntry
import dog.beepboop.velvet.controlServer.models.CommandEntryId
import dog.beepboop.velvet.controlServer.models.InvokeCommandRequest
import dog.beepboop.velvet.controlServer.repositories.CommandRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

@Service
class CooldownService(private val actionRepo: CommandRepository) {
    fun setLastUse(channelId: Int, actionId: String, lastUse: Date) {
        actionRepo.findByIdOrNull(CommandEntryId(channelId,actionId))?.let {
            it.lastUse = lastUse
            actionRepo.save(it)
        }
    }
}

fun secondsSinceExecution(command: CommandEntry) : Long = (System.currentTimeMillis().milliseconds - command.lastUse.time.milliseconds).inWholeSeconds
fun isCommandOffCooldown(command: CommandEntry, tier: Int):Boolean = secondsSinceExecution(command) > command.cooldown[tier]
fun isCommandOffCooldown(command: CommandEntry, request: InvokeCommandRequest): Boolean = secondsSinceExecution(command) > command.cooldown[command.bitCost.indexOf(request.bits)]