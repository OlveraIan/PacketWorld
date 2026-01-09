/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo.peticion;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ian_e
 */
public class ClientePeticion {
    @NotNull(message="El nombre es obligatorio")
    private String nombre;
    @NotNull(message="El apellido paterno es obligatorio")
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    @NotNull(message="El codigo postal es obligatorio")
    private String codigoPostal;
    @NotNull(message="La calle es obligatoria")
    private String calle;
    private Integer numero;
    private String colonia;
    @NotNull(message="El pais es obligatorio")
    private String pais;
    @NotNull(message="El estado es obligatorio")
    private String estado;
    
    public ClientePeticion(){};

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
