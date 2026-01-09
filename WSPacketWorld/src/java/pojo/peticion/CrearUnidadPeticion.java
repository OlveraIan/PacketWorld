/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo.peticion;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author ian_e
 */
public class CrearUnidadPeticion {
    @NotNull(message = "La marca es obligatoria")
    private String marca;

    @NotNull(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    @NotNull(message = "El VIN es obligatorio")
    @Size(min = 17, max = 17, message = "El VIN debe tener exactamente 17 caracteres")
    @Pattern(regexp = "^[^\\s]+$", message = "El VIN no debe contener espacios")
    private String vin;

    @NotNull(message = "El tipo de propulsión es obligatorio")
    private Integer tipoPropulsion;

    public CrearUnidadPeticion() {
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getTipoPropulsion() {
        return tipoPropulsion;
    }

    public void setTipoPropulsion(Integer tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }
}
