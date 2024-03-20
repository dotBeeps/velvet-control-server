package dog.beepboop.velvet.controlServer.models

data class UserJwt(
    val channelId: String,
    val exp: Int?,
    val isUnlinked: Boolean = false,
    val opaqueUserId: String?,
    val pubsubPerms: Map<String, Array<String>>?,
    val userId: String?,
    val role: String = "external",
    )
