package dog.beepboop.velvet.controlServer.models

data class TwitchAuthToken(val accessToken: String, val expiresIn: Int, val refreshToken: String, val scope: List<String>, val tokenType: String)
