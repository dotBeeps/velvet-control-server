package dog.beepboop.velvet.controlServer.controllers

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import dog.beepboop.velvet.controlServer.models.CommandEntry
import dog.beepboop.velvet.controlServer.models.CommandEntryId
import dog.beepboop.velvet.controlServer.repositories.CommandRepository
import dog.beepboop.velvet.controlServer.services.CommandService
import dog.beepboop.velvet.controlServer.services.JwtService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Integer.parseInt
import kotlin.math.log

@RestController
class ConfigController(private val commandRepo: CommandRepository, private val commandService: CommandService, private val jwtService: JwtService) {

    private val logger = KotlinLogging.logger {}
    @GetMapping("/config/{channelId}")
    fun getChannelCommandsForConfig(@PathVariable channelId: String): ResponseEntity<*> {
        val commands = commandService.getCommandsWithChannelOverrides(parseInt(channelId));
        return ResponseEntity(commands, HttpStatus.OK)
    }

    @PutMapping("/config/{channelId}/{commandId}")
    fun putChannelCommandConfig(@PathVariable channelId: String, @PathVariable commandId: String, @RequestBody options: CommandEntry): ResponseEntity<*> {
        val baseCommand = commandRepo.findById(CommandEntryId(0, commandId)).get()
        baseCommand.id = CommandEntryId(parseInt(channelId), commandId)
        baseCommand.enabled = options.enabled
        baseCommand.cooldown = options.cooldown
        baseCommand.duration = options.duration
        baseCommand.bitCost = options.bitCost
        commandRepo.save(baseCommand)
        return ResponseEntity("OK", HttpStatus.OK)
    }

}