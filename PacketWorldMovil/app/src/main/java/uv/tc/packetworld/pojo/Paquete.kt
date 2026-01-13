package uv.tc.packetworld.pojo

data class Paquete(
    val idPaquete: Int,
    val idEnvio: Int,
    val descripcion: String,
    val peso: Double,
    val alto: Double,
    val ancho: Double,
    val profundidad: Double
)