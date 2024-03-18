package dog.beepboop.velvet.controlServer.services

import dog.beepboop.velvet.controlServer.models.Action
import dog.beepboop.velvet.controlServer.models.Script
import dog.beepboop.velvet.controlServer.repositories.ActionRepo
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class CooldownService(private val actionRepo: ActionRepo) {

    fun getCooldown(channelId: Int, category: String, name: String): Long {
        val action = actionRepo.findByChannelIdAndCategoryAndCommand(channelId, category, name)
        action?.let {
            return it.cooldown - ((System.currentTimeMillis() - it.lastUse.time)/ 1000)
        }
        return 0;
    }

    fun setCooldown(channelId: Int, category: String, name: String, command: String = "", cooldown: Int = 0) {
        actionRepo.findByChannelIdAndCategoryAndCommand(channelId, category, name)?.let {
            // Update existing command.
            actionRepo.save(it.copy(
                cooldown = cooldown
            ))
        } ?: run {
            // Insert new command.
            actionRepo.save(
                Action(
                    id = "${channelId}/${category}/${command}",
                    channelId = channelId,
                    category = category,
                    name = name,
                    command = command,
                    script = Script(),
                    cooldown = cooldown
                )
            )
        }
    }

    fun setLastUse(channelId: Int, category: String, command: String, lastUse: Date) {
        actionRepo.findByChannelIdAndCategoryAndCommand(channelId, category, command)?.let {
            actionRepo.save(it.copy(lastUse = it.lastUse))
        }
    }
}