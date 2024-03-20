package dog.beepboop.velvet.controlServer.services

import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.helix.domain.SendPubSubMessageInput
import dog.beepboop.velvet.controlServer.models.TwitchAuthToken
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

@Service
class TwitchService(
    private val jwtService: JwtService,
    @Value("\${twitch.auth.client.id}") private val clientId: String,
    @Value("\${twitchClientSecret}") private val clientSecret: String
) {
    private val logger = KotlinLogging.logger {}
    private val twitchClient = TwitchClientBuilder.builder()
        .withEnableEventSocket(true)
        .withEnableHelix(true)
        .withClientId(clientId)
        .withClientSecret(clientSecret)
        .build()

    fun sendPubSubBroadcast(channelId: String, message: String) {
        val token = jwtService.generateAdminSenderJwt(channelId)
        val pubSubMessage = SendPubSubMessageInput(listOf("broadcast"), channelId, false, message)
        twitchClient.helix.sendExtensionPubSubMessage(token,clientId,pubSubMessage).execute()
    }

    fun getTwitchTokenFromAuthCode(code: String): TwitchAuthToken {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val body = mapOf(
            clientId to "client_id",
            clientSecret to "client_secret",
            code to "code",
            "authorization_code" to "grant_type",
            "https://127.0.0.1:3000" to "redirect_uri"
        )
        val resp = restTemplate.postForObject<TwitchAuthToken>("https://id.twitch.tv/oauth2/token")
        return resp
    }

    fun getChannelIdFromBearer(bearer: String): String {
        val userList = twitchClient.helix.getUsers(bearer,null,null).execute()
        return userList.users[0].id
    }

    fun disableStreamEventListener(channel: String) = twitchClient.clientHelper.disableStreamEventListener(channel)
    fun enableStreamEventListener(channel: String) = twitchClient.clientHelper.enableStreamEventListener(channel)
}