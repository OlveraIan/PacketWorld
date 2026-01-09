/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.pojo;

/**
 *
 * @author DIANA
 */
public class Envio {

    private Integer idEnvio;
    private Integer idCliente;
    private Double costo;
    private Integer numGuia;
    private Integer idOrigen;
    private Integer idDestino;
    private Integer idEstatus;
    private String curp;

    public Envio() {
    }

    public Envio(Integer idEnvio, Integer idCliente, Double costo, Integer numGuia, Integer idOrigen, Integer idDestino, Integer idEstatus, String curp) {
        this.idEnvio = idEnvio;
        this.idCliente = idCliente;
        this.costo = costo;
        this.numGuia = numGuia;
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.idEstatus = idEstatus;
        this.curp = curp;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getNumGuia() {
        return numGuia;
    }

    public void setNumGuia(Integer numGuia) {
        this.numGuia = numGuia;
    }

    public Integer getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

}

