/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.dto;

import com.google.gson.Gson;
import javax.ws.rs.core.Response;

/**
 *
 * @author ian_e
 * @param <T>
 */
public class Mensaje<T> {
    private Integer codigo;
    private Boolean error;
    private T contenido;
    private String mensaje;

    public Mensaje() {
    }

    public Mensaje(Boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }
    
    public static <T> Response correcto(String mensaje, T contenido) {
        Mensaje mensajeCorrecto = new Mensaje();
        mensajeCorrecto.setCodigo(200);
        mensajeCorrecto.setError(false);
        mensajeCorrecto.setMensaje(mensaje);
        mensajeCorrecto.setContenido(contenido);
        Gson gson = new Gson();
        return Response.ok().entity(gson.toJson(mensajeCorrecto)).build();
    }
    
    public static <T> Response incorrecto(Integer codigo, String mensaje, T contenido) {
        Mensaje mensajeError = new Mensaje();
        mensajeError.setCodigo(codigo);
        mensajeError.setError(false);
        mensajeError.setMensaje(mensaje);
        mensajeError.setContenido(contenido);
        Gson gson = new Gson();
        return Response.status(codigo).entity(gson.toJson(mensajeError)).build();
    }
    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public T getContenido() {
        return contenido;
    }

    public void setContenido(T datos) {
        this.contenido = datos;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
