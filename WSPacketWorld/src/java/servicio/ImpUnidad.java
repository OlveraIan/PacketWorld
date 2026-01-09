/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.core.Response;
import mybatis.MyBatisUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.Unidad;
import pojo.peticion.CrearUnidadPeticion;
import pojo.peticion.DarBajaUnidadPeticion;
import pojo.peticion.EditarUnidadPeticion;
import pojo.respuesta.Mensaje;
import ws.ExceptionManager;

/**
 *
 * @author ian_e
 */
public class ImpUnidad {

    public static Response registrarUnidad(CrearUnidadPeticion unidad) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int resultado = conexionBD.insert("unidad.insertar", unidad);
                conexionBD.commit();
                if (resultado > 0) {
                    return Mensaje.correcto("Unidad registrada con éxito", resultado);
                }
                return Mensaje.incorrecto(500, "No se pudo registrar la unidad", null);
            } catch (PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Servicio no disponible", null);
    }

    public static Response editarUnidad(String idInterno, EditarUnidadPeticion peticion) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new LinkedHashMap<>();
                parametros.put("idInterno", idInterno);
                parametros.put("marca", peticion.getMarca());
                parametros.put("modelo", peticion.getModelo());
                parametros.put("anio", peticion.getAnio());
                parametros.put("idPropulsion", peticion.getIdPropulsion());

                int resultado = conexionBD.update("unidad.editarUnidad", parametros);
                conexionBD.commit();
                if (resultado > 0) {
                    return Mensaje.correcto("Unidad " + idInterno + " editada con éxito", resultado);
                }
                return Mensaje.incorrecto(404, "Unidad no encontrada o sin cambios", null);
            } catch (PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Servicio no disponible", null);
    }

    public static Response darBajaUnidad(String idInterno, DarBajaUnidadPeticion peticion) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new LinkedHashMap<>();
                parametros.put("idInterno", idInterno);
                parametros.put("idEstado", peticion.getIdEstado());
                parametros.put("razon", peticion.getDescripcion());
                int resultado = conexionBD.delete("unidad.bajaUnidad", parametros);
                conexionBD.commit();
                if (resultado > 0) {
                    return Mensaje.correcto("Se dio de baja la unidad correctamente", resultado);
                }
                return Mensaje.incorrecto(404, "No se encontró la unidad: " + idInterno, null);
            } catch (PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            }catch (Exception e) {
                return Mensaje.incorrecto(500, "Error al eliminar", e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Servicio no disponible", null);
    }

    public static Response obtenerUnidades() {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {                
                List<Unidad> respuesta = conexionBD.selectList("unidad.todas");
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron unidades", respuesta);
                }
                return Mensaje.correcto("Unidades encontradas", respuesta);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Sin conexión a la base de datos", null);
    }
    
    public static Response obtenerUnidades(String idInterno, String vin, String marca) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                HashMap<String, Object> parametros = new LinkedHashMap<>();
                parametros.put("idInterno", idInterno);
                parametros.put("vin", vin);
                parametros.put("marca", marca);
                
                List<Unidad> respuesta = conexionBD.selectList("unidad.obtenerUnidad", parametros);
                if (respuesta.isEmpty()) {
                    return Mensaje.incorrecto(404, "No se encontraron unidades", respuesta);
                }
                return Mensaje.correcto("Unidades encontradas", respuesta);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Sin conexión a la base de datos", null);
    }
    
    public static Response asignarUnidad(String idInterno, String numLicencia) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("idInterno", idInterno);
                parametros.put("numLicencia", numLicencia);
                
                int resultado = conexionBD.update("unidad.asignarUnidad", parametros);
                if (resultado > 0) {
                    return Mensaje.correcto("Unidad asignada al conductor: " + numLicencia, parametros);
                }
                return Mensaje.incorrecto(404, "Unidad no encontrada o sin cambios", null); 
            } catch (PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            } catch (Exception e) {
                return Mensaje.incorrecto(500, "No se pudo asignar la unidad, porfavor intentelo mas tarde", null);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Sin conexion a la base de datos", null);
    }
    
    public static Response desasignarUnidad(String idInterno) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {               
                int resultado = conexionBD.update("unidad.desasignarUnidad", idInterno);
                if (resultado > 0) {
                    return Mensaje.correcto("Se desasigno la unidad " + idInterno + " correctamente", null);
                }
                return Mensaje.incorrecto(404, "Unidad no encontrada o sin cambios", null); 
            } catch (PersistenceException e) {
                return ExceptionManager.manejarErrorSql(e);
            } catch (Exception e) {
                return Mensaje.incorrecto(500, "No se pudo desasignar la unidad, porfavor intentelo mas tarde", null);
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Sin conexion a la base de datos", null);
    }
}
