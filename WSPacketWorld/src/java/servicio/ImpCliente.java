/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.core.Response;
import mybatis.MyBatisUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.peticion.ClientePeticion;
import pojo.respuesta.Mensaje;
import pojo.respuesta.Respuesta;
import ws.ExceptionManager;

/**
 *
 * @author DIANA
 */
public class ImpCliente {
    
    public static Response registrarCliente(ClientePeticion cliente){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                int resultado = conexionBD.insert("cliente.insertar", cliente);
                conexionBD.commit();
                if(resultado > 0){
                    return Mensaje.correcto("Cliente registrado con exito", resultado);
                }else{
                    return Mensaje.incorrecto(500, "No se puede registrar al cliente, intentelo mas tarde", null);
                }
            }catch(PersistenceException e){
                return ExceptionManager.manejarErrorSql(e);
            }catch(Exception e){
                return Mensaje.incorrecto(500, "Error en el servidor", e.getStackTrace());
            }finally {
                conexionBD.close();
            }
            
        }
        return Mensaje.incorrecto(500, "Por el momento el servicio no esta disponible", null);
    }
    
    public static Response editarCliente(String correoClienteEditar, ClientePeticion peticion){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                peticion.setCorreo(correoClienteEditar);
                int resultado = conexionBD.update("cliente.editarCliente", peticion);
                conexionBD.commit();
                if(resultado > 0){
                    return Mensaje.correcto("Cliente: " + peticion.getNombre() + " editado con exito", resultado);
                }else{
                    return Mensaje.incorrecto(500, "No se pudo editar al cliente, intentelo mas tarde", resultado);
                }
            }catch(PersistenceException e){
                return ExceptionManager.manejarErrorSql(e);
            }catch(Exception e){
                e.printStackTrace();
                return Mensaje.incorrecto(500, "Error en el servidor", e.getStackTrace());
            }finally {
                conexionBD.close();
            }
            
        }
        return Mensaje.incorrecto(500, "Por el momento el servicio no esta disponible", null);
    }
    
    public static Response eliminarCliente (String correo) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                Integer numEliminados = conexionBD.delete("cliente.eliminarCliente", correo);
                conexionBD.commit();
                if (numEliminados > 0) {
                    return Mensaje.correcto(("Se elimino correctamente el cliente con correo: " + correo), numEliminados);
                } else {
                    return Mensaje.incorrecto(404, "No se encontro al cliente con correol: " + correo, numEliminados);
                }
            } catch (PersistenceException ex) {
                return ExceptionManager.manejarErrorSql(ex);
            } catch (Exception ex) {
                ex.printStackTrace();
                return Mensaje.incorrecto(500, "Error al eliminar", ex.getStackTrace());
            }finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Por el momento el servicio no esta disponible", null);
    }
    
    public static Response obtenerClientes(){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                List<Cliente> respuesta = conexionBD.selectList("cliente.todos");
                
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron clientes", respuesta);
                }
                return Mensaje.correcto("Clientes encontrados con exito", respuesta);
            }catch(PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            }catch(Exception e){
                return Mensaje.incorrecto(500, "Error en el servidor", e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        }
        return Mensaje.incorrecto(500, "Sin conexion con la base de datos", null);
    }
    public static Response obtenerCliente(String nombre, String correo, String numTelefono){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,Object> parametros = new LinkedHashMap<>();
                parametros.put("nombre", nombre);
                parametros.put("correo", correo);
                parametros.put("numTelefono", numTelefono);
                List<Cliente> respuesta = conexionBD.selectList("cliente.obtenerCliente", parametros);
                
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron clientes", respuesta);
                }
                return Mensaje.correcto("Clientes encontrados con exito", respuesta);
            }catch(PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            }catch(Exception e){
                return Mensaje.incorrecto(500, "Error en el servidor", e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        }
        return Mensaje.incorrecto(500, "Sin conexion con la base de datos", null);
    }
}
