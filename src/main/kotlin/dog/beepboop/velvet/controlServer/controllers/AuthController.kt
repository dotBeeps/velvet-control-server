package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.services.JwtService
import dog.beepboop.velvet.controlServer.services.TwitchService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val twitchService: TwitchService, private val jwtService: JwtService) {
    @GetMapping("/auth")
    fun getJwtTokenFromAuthCode(@RequestParam code: String): ResponseEntity<*> {
        val token = twitchService.getTwitchTokenFromAuthCode(code)
        val channel = twitchService.getChannelIdFromBearer(token.accessToken)
        val jwt = jwtService.generateModJwt(code)
        return ResponseEntity(mapOf(jwt to "token"), HttpStatus.OK)
    }
}