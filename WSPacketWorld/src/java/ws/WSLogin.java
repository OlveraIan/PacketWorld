/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.validation.Valid;
import servicio.ImpLogin;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pojo.peticion.LoginColaboradorPeticion;

@Path("login")
public class WSLogin {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WSLogin
     */
    public WSLogin() {
    }
 
    @Path("colaborador")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesionColaborador(
            @Valid LoginColaboradorPeticion login
    ){
        return ImpLogin.validarSesionColaborador(login);
    }
    
    
    
}

