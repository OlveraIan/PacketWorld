/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.ibatis.exceptions.PersistenceException;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.respuesta.Mensaje;
import pojo.peticion.CrearColaboradorPeticion;
import pojo.peticion.EditarColaboradorPeticion;
import ws.ExceptionManager;

/**
 *
 * @author DIANA
 */
public class ImpColaborador {
    public static Response registrarColaborador(CrearColaboradorPeticion colaborador){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                if(
                    colaborador.getIdRol() == 3 // Conductor 
                    && (colaborador.getNumLicencia() == null 
                    || colaborador.getNumLicencia().trim().isEmpty())
                ) {
                    return Mensaje.incorrecto(400, "La licencia es obligatoria para conductores", null);
                }
                int resultado = conexionBD.insert("colaborador.insertar", colaborador);
                conexionBD.commit();
                if(resultado > 0){
                    return Mensaje.correcto("Colaborador registrado con exito", resultado);
                }else{
                    return Mensaje.incorrecto(500, "No se puede registrar al colaborador, intentelo mas tarde", null);
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
    
    public static Response editarColaborador(Integer numeroColaborador, EditarColaboradorPeticion peticion){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,Object> parametros = new LinkedHashMap<>();
                parametros.put("numeroPersonal", numeroColaborador);
                
                Colaborador colaborador = conexionBD.selectOne("colaborador.obtenerColaborador", parametros);
               
                if(
                    colaborador != null
                    && "Conductor".equals(colaborador.getNombreRol())
                    && (peticion.getNumLicencia() == null 
                    || peticion.getNumLicencia().trim().isEmpty())
                ) {
                    return Mensaje.incorrecto(400, "La licencia es obligatoria para conductores", null);
                }
                
                parametros.put("nombre", peticion.getNombre());
                parametros.put("apellidoPaterno", peticion.getApellidoPaterno());
                parametros.put("apellidoMaterno", peticion.getApellidoMaterno());
                parametros.put("curp", peticion.getCurp());
                parametros.put("correo", peticion.getCorreo());
                parametros.put("password", peticion.getPassword());
                parametros.put("idSucursal", peticion.getIdSucursal());
                parametros.put("numLicencia", peticion.getNumLicencia());
                parametros.put("foto", peticion.getFoto());

                int resultado = conexionBD.update("colaborador.editarColaborador", parametros);
                conexionBD.commit();
                if(resultado > 0){
                    return Mensaje.correcto("Colaborador " + numeroColaborador + " editado con exito", resultado);
                }else{
                    return Mensaje.incorrecto(500, "No se puede editar al colaborador, intentelo mas tarde", resultado);
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
    
    public static Response eliminarColaborador (String numPersonal) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                Integer numEliminados = conexionBD.delete("colaborador.eliminarColaborador", numPersonal);
                conexionBD.commit();
                if (numEliminados > 0) {
                    return Mensaje.correcto(("Se elimino correctamente el colaborador con numero de persona: " + numPersonal), numEliminados);
                } else {
                    return Mensaje.incorrecto(404, "No se encontro al colaborador con numero de personal: " + numPersonal, numEliminados);
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
    
    public static Response obtenerColaborador(){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                List<Colaborador> respuesta = conexionBD.selectList("colaborador.todos");
                
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron colaboradores", respuesta);
                }
                return Mensaje.correcto("Colaboradores encontrados con exito", respuesta);
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
    public static Response obtenerColaborador(Integer numPersonal, String nombre, Integer rol){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,Object> parametros = new LinkedHashMap<>();
                parametros.put("numeroPersonal", numPersonal);
                parametros.put("nombre", nombre);
                parametros.put("rol", rol);
                List<Colaborador> respuesta = conexionBD.selectList("colaborador.obtenerColaborador", parametros);
                
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron colaboradores", respuesta);
                }
                return Mensaje.correcto("Colaboradores encontrados con exito", respuesta);
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

