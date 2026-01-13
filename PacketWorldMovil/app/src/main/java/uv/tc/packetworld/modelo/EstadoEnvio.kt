package uv.tc.packetworld.modelo

enum class EstadoEnvio(val valor: String) {
    EN_SUCURSAL("En sucursal"),
    EN_TRANSITO("En transito"),
    PROCESADO("Procesado"),
    DETENIDO("Detenido"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado")
}
