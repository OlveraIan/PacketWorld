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
public class Paquete {
    private Integer idPaquete;
    private String descripcion;
    private Float peso;
    private Float alto;
    private Float ancho;
    private Integer idEnvio;
    private String numGuia;
    private String profundidad;
    private String envio;
    private String estatus;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, String descripcion, Float peso, Float alto, Float ancho, Integer idEnvio, String numGuia, String profundidad, String envio, String estatus) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.idEnvio = idEnvio;
        this.numGuia = numGuia;
        this.profundidad = profundidad;
        this.envio = envio;
        this.estatus = estatus;
    }
    

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }


    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAlto() {
        return alto;
    }

    public void setAlto(Float alto) {
        this.alto = alto;
    }

    public Float getAncho() {
        return ancho;
    }

    public void setAncho(Float ancho) {
        this.ancho = ancho;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNumGuia() {
        return numGuia;
    }

    public void setNumGuia(String numGuia) {
        this.numGuia = numGuia;
    }

    public String getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(String profundidad) {
        this.profundidad = profundidad;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }
    

}
