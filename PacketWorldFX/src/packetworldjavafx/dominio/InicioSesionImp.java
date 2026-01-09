/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.dominio;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import packetworldjavafx.conexion.ConexionAPI;
import packetworldjavafx.dto.Mensaje;
import packetworldjavafx.dto.RespuestaInicioSesion;
import packetworldjavafx.pojo.RespuestaHTTP;
import packetworldjavafx.utilidades.Constantes;

/**
 *
 * @author ian_e
 */
public class InicioSesionImp {
    public static Mensaje verificarCredenciales (String noPersonal, String password){
        Mensaje respuesta = new Mensaje();
        String URL = Constantes.URL_WS+"login/colaborador/";
        String parametros = "numPersonal=" +noPersonal+"&password="+password;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL,Constantes.METODO_POST,parametros,Constantes.CONTENT_FORM);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            try{
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), Mensaje.class);
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
            
        }else{
            //ERROR en peticion
            respuesta.setError(true);
            switch(respuestaAPI.getCodigo()){
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje("Lo sentimos hubo un  error al leer la informacion");
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje("Lo sentimos la peticion no tiene el formato correcto");
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    respuesta.setMensaje("Credenciales invalidas");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hubo un error con la respuesta del servicio");
            }
        }
        return respuesta;
    }
}
