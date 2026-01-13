package uv.tc.packetworld.red
//Inicializa el servicio de Retrofit
object PacketWorldSingleton {
    val apiService: PacketWorldAPIService by lazy {
        retrofit.create(PacketWorldAPIService::class.java)
    }
}