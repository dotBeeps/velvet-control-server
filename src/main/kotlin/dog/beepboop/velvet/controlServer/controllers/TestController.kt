package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.services.JwtService
import dog.beepboop.velvet.controlServer.services.TwitchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(val twitchService: TwitchService, val jwtService: JwtService) {
    @PostMapping("/broadcast/test")
    fun broadcast(@RequestBody body: String) {
        twitchService.sendPubSubBroadcast("127844855", body)
    }

    @GetMapping("/clienttoken")
    fun getClientToken(@RequestParam channelId: String): String {
        return jwtService.generateListenerJwt(channelId)
    }
}