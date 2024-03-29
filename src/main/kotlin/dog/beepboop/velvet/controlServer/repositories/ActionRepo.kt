package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Action
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ActionRepo : CrudRepository<Action, String> {
    fun findAllByChannelId(channelId: Int): List<Action>
    fun findAllByChannelIdAndCategory(channelId: Int, category: String): List<Action>
    fun findByChannelIdAndCategoryAndCommand(channelId: Int, category: String, name: String): Action?
}