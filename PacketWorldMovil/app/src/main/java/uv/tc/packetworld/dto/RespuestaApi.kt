package uv.tc.packetworld.dto

data class LoginApiResponse<T>(
    val error: Boolean,
    val mensaje: String,
    val colaborador: T?
)
