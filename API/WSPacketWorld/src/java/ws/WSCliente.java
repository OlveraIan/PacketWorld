/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpCliente;
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
import pojo.Cliente;
import pojo.Mensaje;
import pojo.Respuesta;

/**
 *
 * @author DIANA
 */
@Path("cliente")
public class WSCliente {
    @Context
    private UriInfo context;
    
    public WSCliente(){
        
    }
    
    @Path("obtenerCliente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerCliente(){
        return ImpCliente.obtenerCliente();   
    }
    
    
    @Path("registrarCliente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarCliente(String jsonCliente){
        
       try{
       Gson gson = new Gson();
        Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
        return ImpCliente.registrarCliente(cliente);
       }catch(Exception e){
        e.printStackTrace();
        throw new BadRequestException();
       }
        
    }
    
    @Path("editarCliente")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta editarCliente(String jsonCliente){
        try{
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
        return ImpCliente.editarCliente(cliente);
        }catch( Exception e){
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
    @GET
    @Path("obtenerClienteCorreo/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerClienteCorreo(@PathParam("correo")String correo){
         
        return ImpCliente.obtenerClienteCorreo(correo);
    }
    
    @GET
    @Path("obtenerClientePorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerClientePorNombre(@PathParam("nombre")String nombre){
         
        return ImpCliente.obtenerClientePorNombre(nombre);
    }
    
    @GET
    @Path("obtenerClientePorTelefono/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerClientePorTelefono(@PathParam("telefono")String telefono){
         
        return ImpCliente.obtenerClientePorTelefono(telefono);
    }
    
    @Path("eliminarCliente")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminarCliente(@QueryParam("correo") String correo) {
        return ImpCliente.eliminarCliente(correo);
    }
    
}
