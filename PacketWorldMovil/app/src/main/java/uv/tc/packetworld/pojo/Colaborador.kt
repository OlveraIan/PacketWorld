package uv.tc.packetworld.pojo

data class Colaborador(
    val id: Int,
    val numeroPersonal: String,
    val nombre: String,
    val apellidoPaterno: String,
    val telefono: String?,
    val correo: String?,
    val idColaborador: Int,
    val idRol: Int,
    val idSucursal: Int,
    val sucursalNombre: String,
    val rolNombre: String,
    val foto: String
)