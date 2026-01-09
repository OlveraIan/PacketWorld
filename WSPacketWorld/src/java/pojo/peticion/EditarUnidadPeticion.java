/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo.peticion;

import javax.validation.constraints.NotNull;
/**
 *
 * @author ian_e
 */
public class EditarUnidadPeticion {
    @NotNull(message = "La marca es obligatoria")
    private String marca;

    @NotNull(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    @NotNull(message = "El tipo de propulsión es obligatorio")
    private Integer idPropulsion;

    public EditarUnidadPeticion() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getIdPropulsion() {
        return idPropulsion;
    }

    public void setIdPropulsion(Integer idPropulsion) {
        this.idPropulsion = idPropulsion;
    }
}
