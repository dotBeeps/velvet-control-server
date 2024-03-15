package dog.beepboop.velvet.controlServer.controllers

import dog.beepboop.velvet.controlServer.models.ActionResponse
import dog.beepboop.velvet.controlServer.repositories.ActionRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MenuController(private val actionRepo: ActionRepo) {

    @GetMapping("/commands/{channel}/{category}")
    fun getCategoryActions(@PathVariable channel: String, @PathVariable category: String): ResponseEntity<*> {
        // Convert Action to ActionResponse to send less unnecessary data.
        val actions = actionRepo.findAllByChannelIdAndCategory(channel, category).map {
            ActionResponse(name = it.name, command = it.command, cooldown = it.cooldown, lastUse = it.lastUse, category = it.category)
        }
        return ResponseEntity(actions,HttpStatus.OK)
    }


}