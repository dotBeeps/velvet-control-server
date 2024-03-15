package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepo : MongoRepository<User, String> {
    fun findByChannelId(channelId: String): User?
    fun findByChannelName(channelName: String): User?
    fun findAllByEnabled(enabled: Boolean): List<User>
}