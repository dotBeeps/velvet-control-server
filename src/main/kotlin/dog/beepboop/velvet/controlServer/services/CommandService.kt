package dog.beepboop.velvet.controlServer.services

import dog.beepboop.velvet.controlServer.models.CommandEntry
import dog.beepboop.velvet.controlServer.repositories.CommandRepository
import org.springframework.stereotype.Service

@Service
class CommandService(private val commandRepo: CommandRepository) {

    fun getCommandsWithChannelOverrides(channelId: Int): List<CommandEntry> {
        val defaults = commandRepo.findAllByChannelId(0)
        val overrides = commandRepo.findAllByChannelId(channelId)
        return defaults.map { c -> overrides.find { o -> o.getCommandId() == c.getCommandId() } ?: c }
    }
}