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
public class EstatusEnvio {
    private String idEstatus;
    private String idEnvio;
    private String estatus;

    public EstatusEnvio() {
    }

    public EstatusEnvio(String idEstatus, String idEnvio, String estatus) {
        this.idEstatus = idEstatus;
        this.idEnvio = idEnvio;
        this.estatus = estatus;
    }
    
    
    public String getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(String idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
    
}
