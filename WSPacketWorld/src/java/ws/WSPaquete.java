///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ws;
//
//import com.google.gson.Gson;
//import servicio.ImpPaquete;
//import java.util.List;
//import javax.validation.Valid;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//import pojo.InfoEnvio;
//import pojo.respuesta.Response;
//import pojo.Paquete;
//import pojo.respuesta.Respuesta;
//
///**
// *
// * @author DIANA
// */
//@Path("paquete")
//public class WSPaquete {
//    @Context
//    private UriInfo context;
//    
//    public WSPaquete() {
//    }
//    
//    @Path("todos")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response obtenerPaquetes(){
//        return ImpPaquete.obtenerPaquete();
//    }
//    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response obtenerPaquetes(
//            @QueryParam("numpersonal")Integer numPersonal,
//            @QueryParam("nombre") String nombre,
//            @QueryParam("rol") Integer idRol
//    ){
////        return ImpPaquete.obtenerPaquete(numPersonal, nombre, idRol);
//    }
//    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response registrarPaquete(@Valid CrearPaquetePeticion peticion){        
//        return ImpPaquete.registrarPaquete(peticion);
//    }
//    @Path("/{numPersonal}")
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response editarPaquete(
//            @PathParam("numPersonal") Integer numPersonal,
//            @Valid EditarPaquetePeticion peticion
//    ){
//        return ImpPaquete.editarPaquete(numPersonal, peticion);
//    }
//    
//    @Path("/{numPersonal}")
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response eliminarPaquete(@PathParam("numPersonal") String numPersonal) {
//        return ImpPaquete.eliminarPaquete(numPersonal);
//    } 
//}
