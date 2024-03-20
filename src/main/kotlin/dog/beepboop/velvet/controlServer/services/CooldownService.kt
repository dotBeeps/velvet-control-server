package dog.beepboop.velvet.controlServer.services

import dog.beepboop.velvet.controlServer.models.Command
import dog.beepboop.velvet.controlServer.models.CommandRepoId
import dog.beepboop.velvet.controlServer.repositories.CommandRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

@Service
class CooldownService(private val actionRepo: CommandRepo) {
    fun setLastUse(channelId: Int, actionId: String, lastUse: Date) {
        actionRepo.findByIdOrNull(CommandRepoId(actionId,channelId))?.let {
            actionRepo.save(it.copy(lastUse = lastUse))
        }
    }
}

fun secondsSinceExecution(command: Command) : Long = (System.currentTimeMillis().milliseconds - command.lastUse.time.milliseconds).inWholeSeconds
fun isCommandOffCooldown(command: Command, tier: Int):Boolean = secondsSinceExecution(command) > command.cooldown[tier]

