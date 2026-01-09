/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.conexion;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import packetworldjavafx.pojo.RespuestaHTTP;
import packetworldjavafx.utilidades.Constantes;
import packetworldjavafx.utilidades.Utilidades;

/**
 *
 * @author ian_e
 */
public class ConexionAPI {
    //Regresa el codigo de respuesta http
    
    public static RespuestaHTTP peticionGET(String URL){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
        URL urlWS = new URL(URL);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
        int codigo = conexionHTTP.getResponseCode();
        if(codigo == HttpURLConnection.HTTP_OK){
        respuesta.setContenido(
        Utilidades.streamToString(conexionHTTP.getInputStream()));
        }
        respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
        respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
        respuesta.setContenido(e.getMessage());
        } catch (IOException ex){
        respuesta.setCodigo(Constantes.ERROR_PETICION);
        respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    public static RespuestaHTTP peticionBody(String URL, String metodoHTTP,String parametros, String contentType){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
        URL urlWS = new URL(URL);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
        // Establece el metodo de la peticion
        conexionHTTP.setRequestMethod(metodoHTTP);
        // Cabecera de la solicitud
        conexionHTTP.setRequestProperty("Content-Type", contentType);
        // Escribe el cuerpo de la respuesta
        conexionHTTP.setDoOutput(true);
        OutputStream os = conexionHTTP.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        os.close();
        int codigo = conexionHTTP.getResponseCode();
        if(codigo == HttpURLConnection.HTTP_OK){
        respuesta.setContenido(
        Utilidades.streamToString(conexionHTTP.getInputStream()));
        }
        respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
        respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
        respuesta.setContenido(e.getMessage());
        } catch (IOException ex){
        respuesta.setCodigo(Constantes.ERROR_PETICION);
        respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }
        public static RespuestaHTTP peticionSinBody(String URL, String metodoHTTP){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
        URL urlWS = new URL(URL);
        HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
        conexionHTTP.setRequestMethod(metodoHTTP);
        int codigo = conexionHTTP.getResponseCode();
        if(codigo == HttpURLConnection.HTTP_OK){
        respuesta.setContenido(
        Utilidades.streamToString(conexionHTTP.getInputStream()));
        }
        respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
        respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
        respuesta.setContenido(e.getMessage());
        } catch (IOException ex){
        respuesta.setCodigo(Constantes.ERROR_PETICION);
        respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }
                              
}
