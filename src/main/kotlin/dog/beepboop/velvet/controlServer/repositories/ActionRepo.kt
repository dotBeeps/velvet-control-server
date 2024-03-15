package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Action
import dog.beepboop.velvet.controlServer.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface ActionRepo : MongoRepository<Action, String> {
    fun findAllByChannelId(channelId: String): List<Action>
    fun findAllByChannelIdAndCategory(channelId: String, category: String): List<Action>
    fun findByChannelIdAndCategoryAndCommand(channelId: String, category: String, name: String): Action?
}