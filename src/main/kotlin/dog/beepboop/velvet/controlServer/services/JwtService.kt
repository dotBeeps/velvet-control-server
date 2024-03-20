package dog.beepboop.velvet.controlServer.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64
import java.util.Date

@Service
class JwtService(@Value("\${twitch.auth.client.id}") private val clientId: String, @Value("\${twitchJwtSecret}") private val clientSecret: String) {

    private val decodedKey = Base64.getDecoder().decode(clientSecret)
    private val secretKey = Keys.hmacShaKeyFor(decodedKey)
    private val serverTokenDuration = 30L

    fun parseJwt(token: String): Jws<Claims> {
        val jwt = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        return jwt
    }

    fun generateAdminSenderJwt(channelId: String): String {
        return generateJwt(channelId, mapOf("send" to arrayOf("*")))
    }

    fun generateListenerJwt(channelId: String): String {
        return generateJwt(channelId, mapOf("listen" to arrayOf("broadcast")))
    }

    fun generateModJwt(channelId: String): String {
        return generateJwt(channelId, mapOf("send" to arrayOf("broadcast"), "listen" to arrayOf("broadcast")))
    }

    fun verifyJwt(token: String): Boolean = Jwts.parser().verifyWith(secretKey).build().isSigned(token)

    fun getJwtClaims(token: String): Claims {
        val claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        return claims.payload
    }

    fun generateJwt(channelId: String, perms: Map<String,Array<String>>): String {
        val jwt = Jwts.builder()
            .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .claim("channel_id", channelId)
            .claim("role", "external")
            .claim("pubsub_perms", perms)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
        return jwt;
    }
}