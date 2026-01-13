package uv.tc.packetworld.pojo


data class Envio(
    val idEnvio: Int,
    val guia: String,
    val idSucursal: Int,
    val idEstadoEnvio: Int,
    val idCliente: Int,
    val nombreCliente: String,
    val apPaternoCliente: String,
    val apMaternoCliente: String,
    val calleCliente: String,
    val numeroCliente: String,
    val coloniaCliente: String,
    val cpCliente: String,
    val sucursal: String,
    val sucursalCalle: String,
    val sucursalCP: String,
    val estadoEnvio: String,
    val costo: Double,
    val telefono:String,
    val correo:String
)