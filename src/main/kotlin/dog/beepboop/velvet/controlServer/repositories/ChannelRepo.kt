package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Channel
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ChannelRepo : CrudRepository<Channel, String> {

}