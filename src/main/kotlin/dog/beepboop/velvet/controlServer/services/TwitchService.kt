package dog.beepboop.velvet.controlServer.services

import com.github.philippheuer.events4j.simple.domain.EventSubscriber
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.events.ChannelGoLiveEvent
import com.github.twitch4j.events.ChannelGoOfflineEvent
import com.github.twitch4j.helix.domain.SendPubSubMessageInput
import dog.beepboop.velvet.controlServer.models.User
import dog.beepboop.velvet.controlServer.repositories.UserRepo
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TwitchService(
    private val userRepo: UserRepo,
    private val jwtService: JwtService,
    @Value("\${twitch.auth.client.id}") private val clientId: String,
    @Value("\${twitch.auth.client.secret}") private val clientSecret: String
) {
    private val logger = KotlinLogging.logger {}
    private val twitchClient = TwitchClientBuilder.builder()
        .withEnableEventSocket(true)
        .withEnableHelix(true)
        .withClientId(clientId)
        .withClientSecret(clientSecret)
        .build()

    init {
        // Enable listening to go live and go offline events for all users with extension enabled.
        val users = userRepo.findAllByEnabled(true).map { user: User -> user.channelId }
        twitchClient.clientHelper.enableStreamEventListener(users)
    }

    @EventSubscriber
    fun onGoLiveEvent(goLiveEvent: ChannelGoLiveEvent) {

    }

    @EventSubscriber
    fun onGoOfflineEvent(goOfflineEvent: ChannelGoOfflineEvent) {

    }

    fun sendPubSubBroadcast(channelId: String, message: String) {
        val token = jwtService.generateSenderJwt(channelId)
        val pubSubMessage = SendPubSubMessageInput(listOf("broadcast"), channelId, false, message)
        twitchClient.helix.sendExtensionPubSubMessage(token,clientId,pubSubMessage).execute()
    }

    fun disableStreamEventListener(channel: String) = twitchClient.clientHelper.disableStreamEventListener(channel)
    fun enableStreamEventListener(channel: String) = twitchClient.clientHelper.enableStreamEventListener(channel)
}