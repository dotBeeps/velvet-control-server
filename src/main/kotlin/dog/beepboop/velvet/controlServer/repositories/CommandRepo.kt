package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Command
import dog.beepboop.velvet.controlServer.models.CommandRepoId
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface CommandRepo : CrudRepository<Command, CommandRepoId> {
    fun findAllByChannelId(channelId: Int): List<Command>
    fun findAllByChannelIdAndCategory(channelId: Int, category: String): List<Command>
    fun findByChannelIdAndCategoryAndCommand(channelId: Int, category: String, name: String): Command?
}