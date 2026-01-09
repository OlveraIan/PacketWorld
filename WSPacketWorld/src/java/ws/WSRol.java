/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import servicio.ImpRol;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Rol;

/**
 *
 * @author DIANA
 */

@Path("rol")
public class WSRol {
       @Context
    private UriInfo context;
       
    public WSRol(){
    }
    @Path("obtenerRol")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> obtenerRol(){
        return ImpRol.obtenerRol();
    }
    
}

    

