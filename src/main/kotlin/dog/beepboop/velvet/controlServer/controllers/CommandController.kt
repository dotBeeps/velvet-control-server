package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.models.*
import dog.beepboop.velvet.controlServer.repositories.CommandRepository
import dog.beepboop.velvet.controlServer.repositories.TransactionRepo
import dog.beepboop.velvet.controlServer.services.*
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant.now
import java.util.*

@RestController
class CommandController(val twitchService: TwitchService, val cooldownService: CooldownService, val commandRepo: CommandRepository, val transactionRepo: TransactionRepo, val jwtService: JwtService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("commands/{channel}/{action}")
    fun invokeCommand(@PathVariable channel: Int, @PathVariable action: String, @RequestBody options: InvokeCommandRequest,
                      @AuthenticationPrincipal principal: Jwt): ResponseEntity<*> {
        val userId = jwtService.getUserIdFromJwt(principal)
        logger.info { "Command invocation:\nUser:$userId\nChannel:$channel\nCommand:$action" }
        options.transactionId?.let {
            logger.info { "Bit transaction received from $userId for ${options.bits}." }
            transactionRepo.findByUserIdAndTwitchTransactionId(userId, it) ?.let {
                logger.warn { "Duplicate transaction from user id: $userId , transaction id: ${options.transactionId}" }
                return ResponseEntity("Duplicate transaction.", HttpStatus.INTERNAL_SERVER_ERROR)
            } ?: run {
                if (!jwtService.verifyJwt(options.transactionId)) {
                    logger.error { "Unable to verify payment JWT from user id: $userId , transaction id ${options.transactionId}" }
                    return ResponseEntity("Could not verify twitch transaction jwt.", HttpStatus.BAD_REQUEST)
                }
            }
        }
        val command = commandRepo.findById(CommandEntryId(channel,action)).get()
        if (!isCommandOffCooldown(command,options)) {
            return ResponseEntity("Command on cooldown.", HttpStatus.LOCKED)
        }

        transactionRepo.save(Transaction(null,userId,channel.toString(),action,
            Date.from(now()),options.transactionId,options.bits))
        twitchService.broadcastCommand(command)
        cooldownService.setLastUse(command.channelId, command.getCommandId() ?: "", Date.from(now()))
        return ResponseEntity("",HttpStatus.OK)
    }

    @GetMapping("commands/{channel}")
    fun getChannelCommands(@PathVariable channel: Int): ResponseEntity<*> {
        val commands = commandRepo.findById(CommandEntryId(channel)).map {
            ActionResponse(name = it.displayName, command = it.getCommandId() ?:"", cooldown = it.cooldown[0], lastUse = it.lastUse, category = it.category)
        }
        return ResponseEntity(commands,HttpStatus.OK)
    }
}