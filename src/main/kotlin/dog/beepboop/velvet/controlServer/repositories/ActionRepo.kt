package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Action
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ActionRepo : CrudRepository<Action, String> {
    fun findAllByChannelId(channelId: String): List<Action>
    fun findAllByChannelIdAndCategory(channelId: String, category: String): List<Action>
    fun findByChannelIdAndCategoryAndCommand(channelId: String, category: String, name: String): Action?
}