/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import servicio.ImpColaborador;
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
import pojo.peticion.CrearColaboradorPeticion;
import pojo.peticion.EditarColaboradorPeticion;


@Path("colaborador")
public class WSColaborador {
    @Context
    private UriInfo context;
    public WSColaborador() {
    }
    
    @Path("todos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerColaboradores(){
        return ImpColaborador.obtenerColaborador();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerColaboradores(
            @QueryParam("numpersonal")Integer numPersonal,
            @QueryParam("nombre") String nombre,
            @QueryParam("rol") Integer idRol
    ){
        return ImpColaborador.obtenerColaborador(numPersonal, nombre, idRol);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarColaborador(@Valid CrearColaboradorPeticion peticion){        
        return ImpColaborador.registrarColaborador(peticion);
    }
    @Path("/{numPersonal}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarColaborador(
            @PathParam("numPersonal") Integer numPersonal,
            @Valid EditarColaboradorPeticion peticion
    ){
        return ImpColaborador.editarColaborador(numPersonal, peticion);
    }
    
    @Path("/{numPersonal}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarColaborador(@PathParam("numPersonal") String numPersonal) {
        return ImpColaborador.eliminarColaborador(numPersonal);
    } 
}

