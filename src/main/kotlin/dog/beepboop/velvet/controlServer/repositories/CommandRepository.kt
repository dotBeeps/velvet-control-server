package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.CommandEntry
import dog.beepboop.velvet.controlServer.models.CommandEntryId
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface CommandRepository : CrudRepository<CommandEntry, CommandEntryId> {
    fun findAllByChannelId(channelId: Int): List<CommandEntry>
}