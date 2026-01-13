package uv.tc.packetworld.util

import android.content.Context
import uv.tc.packetworld.pojo.Colaborador

//Guardamos la sesion del colaborador en SharedPreferences
object AdministradorDeSesion {
    private const val NOMBRE_ARCHIVO = "Sesion"
    private const val COLABORADOR_ID = "colaborador_id"
    private const val COLABORADOR_NOMBRE = "nombre"

    fun guardarColaborador(context: Context, colaborador: Colaborador) {
        val sharedPreferences = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
            .putString(COLABORADOR_ID, colaborador.numeroPersonal)
            .putString(COLABORADOR_NOMBRE, colaborador.nombre)
            .apply()
    }
    fun obtenerRefColaborador(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        return sharedPreferences.getString(COLABORADOR_ID, null)
    }
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        return sharedPreferences.contains(COLABORADOR_ID)
    }
    fun vaciarSesion(context: Context) {
        val sharedPreferences = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit().clear().apply()
    }

}