package uv.tc.packetworld.pojo

data class LoginResult(
    val error: Boolean,
    val colaborador: Colaborador? = null,
    val message: String = ""
)