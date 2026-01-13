package uv.tc.packetworld.red
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import uv.tc.packetworld.dto.LoginApiResponse
import uv.tc.packetworld.pojo.Colaborador
import uv.tc.packetworld.pojo.Envio
import uv.tc.packetworld.pojo.Paquete
import uv.tc.packetworld.util.Constantes

// Configuración de Retrofit
/*El conversor le indica a Retrofit qué hacer con los datos que obtiene del servicio web.
En este caso, Retrofit tendría que recuperar una respuesta JSON del servicio web y mostrarla como Gson.
Por ultimo lo convertimos al objeto que necesitamos*/

val retrofit = Retrofit.Builder()
    .baseUrl(Constantes().URL_WS)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface PacketWorldAPIService {

    @FormUrlEncoded
    @POST("autenticacion/colaborador")
    suspend fun login(
        @Field("numeroPersonal") numeroPersonal: String,
        @Field("password") password: String
    ): Response<LoginApiResponse<Colaborador>>

    @GET("envios/obtenerEnviosConductor/{busqueda}")
    suspend fun getEnviosPorConductor(
        @Path("busqueda") numeroPersonal: String
    ): Response<List<Envio>>

    @GET("envios/buscar/{busqueda}")
    suspend fun getDetalleEnvio(
        @Path("busqueda") envioId: String
    ): Response<List<Envio>>

    @PUT("envios/editar")
    suspend fun cambiarEstado(json: String
    ): Response<String>

    @GET("paquete/buscar/{busqueda}")
    suspend fun getPaquetesPorGuia(
        @Path("busqueda") numeroGuia: String
    ): Response<List<Paquete>>

    //@PUT("colaborador")


}
