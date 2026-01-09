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
public class DarBajaUnidadPeticion {
    @NotNull(message = "El nuevo estado es obligatorio")
    private Integer idEstado;

    @NotNull(message = "Debe proporcionar una descripción o motivo")
    private String descripcion;

    public DarBajaUnidadPeticion() {
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
