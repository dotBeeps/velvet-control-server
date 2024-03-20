package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.models.ActionResponse
import dog.beepboop.velvet.controlServer.models.CommandRepoId
import dog.beepboop.velvet.controlServer.repositories.CommandRepo
import dog.beepboop.velvet.controlServer.services.CooldownService
import dog.beepboop.velvet.controlServer.services.TwitchService
import dog.beepboop.velvet.controlServer.services.isCommandOffCooldown
import dog.beepboop.velvet.controlServer.services.secondsSinceExecution
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@RestController
class CommandController(val twitchService: TwitchService, val cooldownService: CooldownService, val commandRepo: CommandRepo) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("commands/{channel}/{action}")
    fun invokeCommand(@PathVariable channel: Int, @PathVariable action: String, @RequestBody options: Map<String,String>): ResponseEntity<*> {
        //
        val command = commandRepo.findByIdOrNull(CommandRepoId(action,channel))

        return ResponseEntity("",HttpStatus.OK)
    }

    @GetMapping("commands/{channel}")
    fun getChannelCommands(@PathVariable channel: Int, @AuthenticationPrincipal principal: Jwt): ResponseEntity<*> {
        val commands = commandRepo.findAllByChannelId(channel).map {
            ActionResponse(name = it.displayName, command = it.getActionId(), cooldown = it.cooldown[0], lastUse = it.lastUse, category = it.category)
        }
        return ResponseEntity(commands,HttpStatus.OK)
    }

    /*
    @PostMapping("/commands/{channel}/{category}/{command}")
    fun invokeCommand(@PathVariable channel: Int, @PathVariable category: String, @PathVariable command: String): ResponseEntity<*> {
        if (cooldownService.getCooldown(channel,category,command) <= 0) {
            val action = actionRepo.findByChannelIdAndCategoryAndCommand(channel,category,command)
            action?.let {
                val script = Json.encodeToString(action.parameters)
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
    */
}