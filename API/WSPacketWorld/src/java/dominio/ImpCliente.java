/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Mensaje;
import pojo.Respuesta;

/**
 *
 * @author DIANA
 */
public class ImpCliente {
    
    public static List<Cliente> obtenerCliente() {
    List<Cliente> lista = new ArrayList();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            HashMap<String, String> parametros = new LinkedHashMap<>();
          lista = conexionBD.selectList("cliente.obtenerCliente", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }    
    
    public static List<Cliente> obtenerClienteCorreo(String correo){
        List<Cliente> lista = new ArrayList();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
           if (conexionBD != null) {
        try {
            HashMap<String, String> parametros = new LinkedHashMap<>();
            parametros.put("correo", correo);
          lista = conexionBD.selectList("cliente.obtenerClienteCorreo", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }
    
    public static List<Cliente> obtenerClientePorNombre(String nombre){
        List<Cliente> lista = new ArrayList();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
           if (conexionBD != null) {
        try {
            HashMap<String, String> parametros = new LinkedHashMap<>();
            parametros.put("nombre", nombre);
          lista = conexionBD.selectList("cliente.obtenerClientePorNombre", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }
    
    public static List<Cliente> obtenerClientePorTelefono(String telefono){
        List<Cliente> lista = new ArrayList();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
           if (conexionBD != null) {
        try {
            HashMap<String, String> parametros = new LinkedHashMap<>();
            parametros.put("telefono", telefono);
          lista = conexionBD.selectList("cliente.obtenerClientePorTelefono", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }
    
    public static Mensaje registrarCliente(Cliente cliente){
     Mensaje msj = new Mensaje();
     SqlSession conexionBD = MyBatisUtil.obtenerConexion();
     if(conexionBD!= null){
         try{
         int resultado =conexionBD.insert("cliente.registrarCliente", cliente);
         conexionBD.commit();
         if(resultado > 0){
             msj.setError(false);
             msj.setMensaje("Cliente registrado con exito");
         }else{
             msj.setError(true);
             msj.setMensaje("No se pudo registrar al cliente, intentarlo mas tarde.");
         }
         }catch(Exception e){
         msj.setError(true);
         msj.setMensaje(e.getMessage());
         }finally{
        conexionBD.close();
        }
     }else{
         msj.setError(true);
         msj.setMensaje("No se pudo establecer conexión a la base de datos:");
     }
    return msj;
    }
    
    
    
    
    public static Respuesta editarCliente( Cliente cliente){
    Respuesta respuesta = new Respuesta();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if(conexionBD!= null){
    try{
         int resultado =conexionBD.update("cliente.editarCliente", cliente);
         conexionBD.commit();
         if(resultado > 0){
             respuesta.setError(false);
             respuesta.setMensaje("El cliente ha sido editado correctamente");
         }else{
             respuesta.setError(true);
             respuesta.setMensaje("No se pudo editar al cliente, intentalo mas tarde.");
         }
         }catch(Exception e){
         respuesta.setError(true);
         respuesta.setMensaje(e.getMessage());
         }finally{
        conexionBD.close();
        }
    } else{
        respuesta.setError(true);
         respuesta.setMensaje("No se pudo establecer conexión a la base de datos");
     }
    return respuesta;
    }
    
    public static Respuesta eliminarCliente(String correo){
    Respuesta respuesta = new Respuesta();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if(conexionBD != null){
        try{
        HashMap<String, String> parametros = new LinkedHashMap<>();
        parametros.put("correo", correo);
        int filasAfectadas = conexionBD.delete("cliente.eliminarCliente" ,parametros);
         if(filasAfectadas > 0){
                respuesta.setError(false);
                respuesta.setMensaje("Cliente eliminado correctamente");
                
            }else{
                respuesta.setError(true);
                respuesta.setMensaje("No se pudo eliminar al cliente");
            }
            conexionBD.commit();
          }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
        
        }finally{
        conexionBD.close();
        }
        
    }else {
         respuesta.setError(true);
         respuesta.setMensaje("Por el momento no se puede consultar la informacion:");
    }
    return respuesta ;
    }
}
