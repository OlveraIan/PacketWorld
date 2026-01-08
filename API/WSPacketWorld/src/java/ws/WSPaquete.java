/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpPaquete;
import java.util.List;
import javax.ws.rs.BadRequestException;
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
import javax.ws.rs.core.UriInfo;
import pojo.InfoEnvio;
import pojo.Mensaje;
import pojo.Paquete;
import pojo.Respuesta;

/**
 *
 * @author DIANA
 */
@Path("paquete")
public class WSPaquete {
    @Context
    private UriInfo context;
    
    public WSPaquete() {
    }
    
    @Path("obtenerPaquete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete> obtenerPaquete(){
        return ImpPaquete.obtenerPaquete();   
    }
    
    @GET
    @Path("obtenerPaqueteEnvio/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete>obtenerPaqueteEnvio(@PathParam("idEnvio")Integer idEnvio){
        if((idEnvio != null) && (idEnvio > 0)){
            return ImpPaquete.obtenerPaqueteEnvio(idEnvio);
        }
        throw new BadRequestException();
    }
    @Path("registrarPaquete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarPaquete(String jsonPaquete){
        
       try{
       Gson gson = new Gson();
        Paquete paquete = gson.fromJson(jsonPaquete, Paquete.class);
        return ImpPaquete.registrarPaquete(paquete);
       }catch(Exception e){
        e.printStackTrace();
        throw new BadRequestException();
       }   
    }
    
    @Path("editarPaquete")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarPaquete(String jsonPaquete){
        try{
        Gson gson = new Gson();
        Paquete paquete = gson.fromJson(jsonPaquete, Paquete.class);
        return ImpPaquete.editarPaquete(paquete);
        }catch( Exception e){
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
     
    @GET
    @Path("obtenerPaquetenumGuia/{numGuia}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paquete>obtenerPaquetenumGuia(@PathParam("numGuia")String numGuia){
        if(numGuia != null){
            return ImpPaquete.obtenerPaquetenumGuia(numGuia);
        }
        throw new BadRequestException();
    }
    @Path("consultarPaquete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public InfoEnvio consultarPaquete(@PathParam("idEnvio") Integer idEnvio) {
        if (idEnvio != null) {
            return ImpPaquete.consultarPaquete(idEnvio);

        } else {
            throw new BadRequestException();
        }
    }
    
    @Path("eliminarPaquete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarPaquete(@QueryParam("idPaquete") Integer idPaquete){
        return ImpPaquete.eliminarPaquete(idPaquete);    
    }
}
