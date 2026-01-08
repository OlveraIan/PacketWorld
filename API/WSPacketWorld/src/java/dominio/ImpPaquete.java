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
import pojo.Envio;
import pojo.InfoEnvio;
import pojo.Mensaje;
import pojo.Paquete;
import pojo.Respuesta;

/**
 *
 * @author DIANA
 */
public class ImpPaquete {
    public static List<Paquete> obtenerPaquete() {
    List<Paquete> lista = new ArrayList();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
          HashMap<String, String> parametros = new LinkedHashMap<>();
          lista = conexionBD.selectList("paquete.obtenerPaquete", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }    
    
    public static List<Paquete> obtenerPaquetenumGuia(String numGuia) {
        List<Paquete> lista = new ArrayList();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("numGuia", numGuia);
                lista = conexionBD.selectList("paquete.obtenerPaquetenumGuia", parametros);
     
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return lista;
    }
    public static List<Paquete> obtenerPaqueteEnvio(Integer idEnvio) {
    List<Paquete> lista = new ArrayList();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            HashMap<String, Integer> parametros = new LinkedHashMap<>();
            parametros.put("idEnvio", idEnvio);
            lista = conexionBD.selectList("paquete.obtenerPaqueteEnvio", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
}
    
    public static Mensaje registrarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
           try{
               int resultado = conexionBD.insert("paquete.registrarPaquete", paquete);
               conexionBD.commit();
               if(resultado > 0){
                   msj.setError(false);
                   msj.setMensaje("Paquete registrado Correctamente");  
               }else{
                   msj.setError(true);
                   msj.setMensaje("No se pudo resgistrar el paquete, intentalo mas tarde ");       
               }          
           }catch(Exception e){
               msj.setError(true);
               msj.setMensaje(e.getMessage());         
           }finally{
               conexionBD.close();        
           }
        }else{
            msj.setError(true);
            msj.setMensaje("No se pudo tener conexion a la base de datos");
            
        }       
        return msj;
    }
    
    public static InfoEnvio consultarPaquete(Integer idEnvio) {

        InfoEnvio respuesta = new InfoEnvio();
        SqlSession conexionBD = mybatis.MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {

            try {
                HashMap<String, Integer> parametros = new LinkedHashMap<>();
                parametros.put("idEnvio", idEnvio);

                Envio envio = conexionBD.selectOne("paquete.consultarPaquete", parametros);

                if (envio != null) {
                    respuesta.setError(Boolean.FALSE);
                    respuesta.setMensaje("Envio #" + envio.getIdEnvio() + " encontrado.");
                    respuesta.setEnvio(envio);
                } else {
                    respuesta.setError(Boolean.TRUE);
                    respuesta.setMensaje("Envio no encontrado");

                }

            } catch (Exception e) {
                respuesta.setError(Boolean.TRUE);
                respuesta.setMensaje(e.getMessage());
            }

        } else {
            respuesta.setError(Boolean.TRUE);
            respuesta.setMensaje("No se puede leer la información");
        }

        return respuesta;
    }
    public static Mensaje editarPaquete( Paquete paquete){
    Mensaje respuesta = new Mensaje();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if(conexionBD!= null){
    try{
         int resultado =conexionBD.update("paquete.editarPaquete", paquete);
         conexionBD.commit();
         if(resultado > 0){
             respuesta.setError(false);
             respuesta.setMensaje("El Paquete ha sido editado con exito");
         }else{
             respuesta.setError(true);
             respuesta.setMensaje("no se pudo editar el paquete, intentarlo mas tarde.");
         }
         }catch(Exception e){
         respuesta.setError(true);
         respuesta.setMensaje(e.getMessage());
         }finally{
        conexionBD.close();
        }
    } else{
        respuesta.setError(true);
         respuesta.setMensaje("No se pudo establecer conexión a la base de datos...");
     }
    return respuesta;
    }
    
    public static Respuesta eliminarPaquete(Integer idPaquete){
    Respuesta respuesta = new Respuesta();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if(conexionBD != null){
        try{
        HashMap<String, Integer> parametros = new LinkedHashMap<>();
        parametros.put("idPaquete", idPaquete);
        int filasAfectadas = conexionBD.delete("paquete.eliminarPaquete" ,parametros);
         if(filasAfectadas > 0){
                respuesta.setError(false);
                respuesta.setMensaje("paquete eliminado");
                
            }else{
                respuesta.setError(true);
                respuesta.setMensaje("No se pudo eliminar el paquete");
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
         respuesta.setMensaje("Por el momento no se puede consultar la informacion");
    }
    return respuesta ;
    }

    
}
