/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.utilidades;

/**
 *
 * @author ian_e
 */
public class Constantes {
    public static final int ERROR_MALFORMED_URL = 1024;
    public static final int ERROR_PETICION = 2048;
    public static final String URL_WS ="http://localhost:8080/WSPacketWorld/ws/";
    
    public static final String METODO_POST = "POST";
    public static final String METODO_PUT = "PUT";
    public static final String METODO_DELETE = "DELETE";
    public static final String METODO_GET = "GET";
    public static final String CONTENT_JSON = "application/json";
    public static final String CONTENT_FORM = "application/x-www-form-urlencoded";
    public static final String MSJ_ERROR_URL = "Lo sentimos hubo un error al intentar conectar con el servicio.";
    public static final String MSJ_ERROR_PETICION = "Lo sentimos la peticion no tiene el formato correcto";
    
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_LISTA = "lista_valores";
}
