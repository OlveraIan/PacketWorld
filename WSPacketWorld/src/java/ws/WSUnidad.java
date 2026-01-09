/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.peticion.CrearUnidadPeticion;
import pojo.peticion.DarBajaUnidadPeticion;
import pojo.peticion.EditarUnidadPeticion;
import servicio.ImpUnidad;

/**
 * REST Web Service
 *
 * @author ian_e
 */
@Path("unidad")
public class WSUnidad {

    @Context
    private UriInfo context;

    public WSUnidad() {}
    
    

    @Path("todas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUnidades() {
        return ImpUnidad.obtenerUnidades();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUnidades(
            @QueryParam("idInterno") String idInterno,
            @QueryParam("vin") String vin,
            @QueryParam("marca") String marca
    ) {
        return ImpUnidad.obtenerUnidades(idInterno, vin, marca);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUnidad(@Valid CrearUnidadPeticion peticion) {
        return ImpUnidad.registrarUnidad(peticion);
    }

    @Path("/{idInterno}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarUnidad(
            @PathParam("idInterno") String idInterno,
            @Valid EditarUnidadPeticion peticion
    ) {
        return ImpUnidad.editarUnidad(idInterno, peticion);
    }

    @Path("/{idInterno}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response darBajaUnidad(
            @PathParam("idInterno") String idInterno,
            @Valid DarBajaUnidadPeticion peticion
    ) {
        return ImpUnidad.darBajaUnidad(idInterno, peticion);
    }
    
    @Path("/{idInterno}/colaborador/{numLicencia}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignarConductor(
            @PathParam("idInterno") String idInterno,
            @PathParam("numLicencia") String numLicencia
    ) {
        return ImpUnidad.asignarUnidad(idInterno, numLicencia);
    }
    
    @Path("/{idInterno}/colaborador")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response desasignarConductor (@PathParam("idInterno") String idInterno) {
        return ImpUnidad.desasignarUnidad(idInterno);
    }
}
  