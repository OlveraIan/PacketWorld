/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpColaborador;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Colaborador;
import pojo.Mensaje;


@Path("colaborador")
public class WSColaborador {
    @Context
    private UriInfo context;

    public WSColaborador() {
    }
    
    @Path("registrarColaborador")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarColaborador(
            @FormParam("nombre") String nombre,
            @FormParam("apellidoPaterno") String apellidoPaterno,
            @FormParam("apellidoMaterno") String apellidoMaterno,
            @FormParam("curp") String curp,
            @FormParam("correo") String correo,
            @FormParam("numPersonal") String numPersonal,
            @FormParam("password") String password,
            @FormParam("idRol") String idRol,
            @FormParam("numLicencia") String numLicencia){
        
        Colaborador colaborador = new Colaborador();
        try{
            HashMap<String,Object> datosFormulario = new HashMap<>();
            datosFormulario.put("nombre",nombre);
            datosFormulario.put("apellidoPaterno",apellidoPaterno);
            datosFormulario.put("apellidoMaterno",apellidoMaterno);
            datosFormulario.put("curp",curp);
            datosFormulario.put("correo",correo);
            datosFormulario.put("numPersonal",numPersonal);
            datosFormulario.put("password",password);
            datosFormulario.put("idRol",idRol);
            datosFormulario.put("numLicencia",numLicencia);
            
            Gson gson = new Gson();
            colaborador = gson.fromJson(gson.toJson(datosFormulario), Colaborador.class);
            System.out.println(colaborador);
            
        }catch(Exception e){
            throw new BadRequestException();
        }
        return ImpColaborador.registrarColaborador(colaborador);
    }
    
    @Path("obtenerPorNoPersonal")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerColaboradorPorNumeroPersonal(
            @FormParam("numPersonal") String numPersonal){
        if((numPersonal != null && !numPersonal.isEmpty()) && numPersonal.length()<=10){
            return ImpColaborador.obtenerColaborador(numPersonal);
        }
        throw new BadRequestException(); 
    }
    
    @Path("obtenerPorRol")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List <Colaborador> obtenerColaboradoresPorRol(
            @FormParam("rol") String rol){
        if((rol != null && !rol.isEmpty()) && rol.length()<=10){
            return ImpColaborador.obtenerColaboradoresPorRol(rol);
        }
        throw new BadRequestException(); 
    }
    
    @Path("obtenerPorNombre")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List <Colaborador> obtenerColaboradoresPorNombre(
            @FormParam("nombre") String nombre){
        if((nombre != null && !nombre.isEmpty()) && nombre.length()<=10){
            return ImpColaborador.obtenerColaboradoresPorNombre(nombre);
        }
        throw new BadRequestException(); 
    }
    
    @Path("obtenerColaboradores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerColaboradoresList(){
        
        return ImpColaborador.obtenerColaboradoresList();   
    }
    
    @Path("editarColaborador")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarColaborador(
            @FormParam("numPersonal") String numPersonal,
            @FormParam("nombre") String nombre,
            @FormParam("apellidoPaterno") String apellidoPaterno,
            @FormParam("apellidoMaterno") String apellidoMaterno,
            @FormParam("curp") String curp,
            @FormParam("correo") String correo,
            @FormParam("password") String password,
            @FormParam("numLicencia") String numLicencia){
        Mensaje msj = new Mensaje();
        
        
        
        try{
            HashMap<String,Object> datosFormulario = new HashMap<>();
            datosFormulario.put("numPersonal",numPersonal);
            datosFormulario.put("nombre",nombre);
            datosFormulario.put("apellidoPaterno",apellidoPaterno);
            datosFormulario.put("apellidoMaterno",apellidoMaterno);
            datosFormulario.put("curp",curp);
            datosFormulario.put("correo",correo);
            datosFormulario.put("password",password);
            datosFormulario.put("numLicencia",numLicencia);
            
            Gson gson = new Gson();
            Colaborador colaborador = gson.fromJson(gson.toJson(datosFormulario), Colaborador.class);
            
            msj = ImpColaborador.editarColaborador(colaborador);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
        return msj;
    }
    
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarColaborador(@FormParam("numPersonal") String numPersonal) {
        Mensaje msj = new Mensaje();
        
        try {
            
            msj = ImpColaborador.eliminarColaborador(numPersonal);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
        
        return msj;
    } 
}

