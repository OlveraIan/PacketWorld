/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author DIANA
 */
public class InfoEnvio {

    private Boolean error;
    private String mensaje;
    private Envio envio;

    public InfoEnvio() {
    }

    public InfoEnvio(Boolean error, String mensaje, Envio envio) {
        this.error = error;
        this.mensaje = mensaje;
        this.envio = envio;
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

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
    
}

    

