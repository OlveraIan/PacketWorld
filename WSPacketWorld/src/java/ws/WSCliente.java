/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pojo.peticion.ClientePeticion;
import servicio.ImpCliente;

/**
 *
 * @author DIANA
 */
@Path("clientes")
public class WSCliente {
    @Context
    private UriInfo context;
    
    public WSCliente(){}  
    
    @Path("todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClientes(){
        return ImpCliente.obtenerClientes();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCliente(
            @QueryParam("nombre")String nombre,
            @QueryParam("numtelefono") String numTelefono,
            @QueryParam("correo")String correo
    ){
        return ImpCliente.obtenerCliente(nombre, correo, numTelefono);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarCliente(@Valid ClientePeticion peticion){        
        return ImpCliente.registrarCliente(peticion);
    }
    
    @Path("/{correo}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarCliente(
            @PathParam("correo") String correo,
            @Valid ClientePeticion peticion
    ){
        return ImpCliente.editarCliente(correo, peticion);
    }
    
    @Path("/{correo}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCliente(@PathParam("correo") String correo) {
        return ImpCliente.eliminarCliente(correo);
    }    
}
