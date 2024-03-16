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
class JwtService(@Value("\${twitchJwtSecret}") private val secretKeyString: String) {

    private val decodedKey = Base64.getDecoder().decode(secretKeyString)
    private val secretKey = Keys.hmacShaKeyFor(decodedKey)
    private val serverTokenDuration = 30L

    fun parseJwt(token: String): Jws<Claims> {
        val jwt = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        return jwt
    }

    fun generateSenderJwt(channelId: String): String {
        val jwt = Jwts.builder()
            .expiration(Date.from(Instant.now().plusSeconds(serverTokenDuration)))
            .claim("channel_id", channelId)
            .claim("role", "external")
            .claim("pubsub_perms", mapOf("send" to arrayOf("*")))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
        return jwt;
    }

    fun generateListenerJwt(channelId: String): String {
        val jwt = Jwts.builder()
            .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .claim("channel_id", channelId)
            .claim("role", "external")
            .claim("pubsub_perms", mapOf("listen" to arrayOf("broadcast")))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
        return jwt;
    }
}