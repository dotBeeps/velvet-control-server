package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.User
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UserRepo : CrudRepository<User, String> {
    fun findByChannelId(channelId: String): User?
    fun findByChannelName(channelName: String): User?
    fun findAllByEnabled(enabled: Boolean): List<User>
}