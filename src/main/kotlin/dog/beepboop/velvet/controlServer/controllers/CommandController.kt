package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.models.UIActionUpdate
import dog.beepboop.velvet.controlServer.repositories.ActionRepo
import dog.beepboop.velvet.controlServer.services.CooldownService
import dog.beepboop.velvet.controlServer.services.TwitchService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class CommandController(val twitchService: TwitchService, val cooldownService: CooldownService, val actionRepo: ActionRepo) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/commands/{channel}/{category}/{command}")
    fun invokeCommand(@PathVariable channel: Int, @PathVariable category: String, @PathVariable command: String): ResponseEntity<*> {
        if (cooldownService.getCooldown(channel,category,command) <= 0) {
            val action = actionRepo.findByChannelIdAndCategoryAndCommand(channel,category,command)
            action?.let {
                val script = Json.encodeToString(action.script)
                twitchService.sendPubSubBroadcast(channel.toString(), script)
                cooldownService.setLastUse(channel,category,command, Date.from(Instant.now()))
                val uiStr = Json.encodeToString(UIActionUpdate(
                    useTime = Instant.now().epochSecond,
                    category = it.category,
                    command = it.command
                ))
                logger.info { "Executing $script" }
                twitchService.sendPubSubBroadcast(channel.toString(), uiStr)
                return ResponseEntity.ok("Confirmed.")
            }
            return ResponseEntity("Couldn't find command.", HttpStatus.NOT_FOUND)
        } else {
            return ResponseEntity(cooldownService.getCooldown(channel,category,command), HttpStatus.LOCKED)
        }
    }
}