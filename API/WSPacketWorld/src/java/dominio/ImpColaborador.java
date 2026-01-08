/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;

/**
 *
 * @author DIANA
 */
public class ImpColaborador {
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                int resultado = conexionBD.insert("colaborador.insertar", colaborador);
                conexionBD.commit();
                if(resultado > 0){
                    msj.setError(false);
                    msj.setMensaje("Colaborador registrado con exito");
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se puede registrar al colaborador, intentelo mas tarde");
                }
            }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
            }
            
        }else{
        msj.setError(true);
        msj.setMensaje("Por el momento el servicio no esta disponible");
        }
        return msj;
    
    }
    
    public static Mensaje editarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                System.out.println(colaborador);
                int resultado = conexionBD.update("colaborador.editar", colaborador);
                conexionBD.commit();
                if(resultado > 0){
                    msj.setError(false);
                    msj.setMensaje("Colaborador " + colaborador.getNumPersonal() + " editado con exito");
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se puede editar al colaborador, intentelo mas tarde");
                }
            }catch(Exception e){
                e.printStackTrace();
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }
            
        }else{
        msj.setError(true);
        msj.setMensaje("Por el momento el servicio no esta disponible");
        }
        return msj;
    }
    
    public static Mensaje eliminarColaborador (String numPersonal) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        
        try {
            if (conexionBD != null) {
                int numEliminados = conexionBD.delete("colaborador.eliminarColaborador", numPersonal);
                conexionBD.commit();
                
                System.out.println(numEliminados);
                
                msj.setError(Boolean.FALSE);
                msj.setMensaje("Se elimino correctamente el colaborador con numero de persona: " + numPersonal);
            } else {
                msj.setError(Boolean.TRUE);
                msj.setMensaje("Por el momento no esta disponible");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            msj.setError(Boolean.TRUE);
            msj.setMensaje("Error al eliminar");
        }
        
        return msj;
    }
    
    public static Colaborador obtenerColaborador(String numPersonal){
        Colaborador respuesta = new Colaborador();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,String> parametros = new LinkedHashMap<>();
                parametros.put("numPersonal", numPersonal);
                Colaborador colaborador = conexionBD.selectOne("colaborador.obtenerColaboradorPorNoPersonal",numPersonal);
                if(colaborador != null){
                   respuesta = colaborador; 
                } 
            }catch(Exception e){
            e.printStackTrace();
            }
        }else{
            throw new NullPointerException();
        }
        return respuesta;
    }
    public static List <Colaborador> obtenerColaboradoresPorNombre(String nombre){
        List <Colaborador> respuesta = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,String> parametros = new LinkedHashMap<>();
                parametros.put("nombre", nombre);
                List <Colaborador> pagosCliente = conexionBD.selectList("colaborador.obtenerPorNombre",parametros);
                System.out.println(pagosCliente.toString());
                if(pagosCliente != null){ 
                   respuesta = pagosCliente; 
                } 
            }catch(Exception e){
            e.printStackTrace();
            }
        }else{
        
        }
        return respuesta;   
    }
    
    public static List <Colaborador> obtenerColaboradoresPorRol(String rol){
        List <Colaborador> respuesta = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                HashMap <String,String> parametros = new LinkedHashMap<>();
                parametros.put("rol", rol);
                List <Colaborador> pagosCliente = conexionBD.selectList("colaborador.obtenerPorRol",parametros);
                System.out.println(pagosCliente.toString());
                if(pagosCliente != null){ 
                   respuesta = pagosCliente; 
                } 
            }catch(Exception e){
            e.printStackTrace();
            }
        }else{
            throw new NullPointerException();
        }
        return respuesta;   
    }
    
    public static List<Colaborador> obtenerColaboradoresList(){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        
        if (conexionBD != null){
            try{
               List<Colaborador>listaColaboradores = conexionBD.selectList("colaborador.obtenerColaboradores");
               if(listaColaboradores != null){
                   return listaColaboradores;  
               }
            }
            catch(Exception e){
               e.printStackTrace();
            }
        }else{
            throw new NullPointerException();    
        }        
        return null;
    }
}

