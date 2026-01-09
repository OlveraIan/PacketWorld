/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo.peticion;

import javax.validation.constraints.NotNull;
/**
 *
 * @author DIANA
 */
public class LoginColaboradorPeticion {
    @NotNull(message = "El numero de personal es requerido")
    private Integer numPersonal;
    @NotNull(message = "La contraseña es requerida")
    private String password;

    public LoginColaboradorPeticion() {
    }

    public Integer getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(Integer numPersonal) {
        this.numPersonal = numPersonal;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
