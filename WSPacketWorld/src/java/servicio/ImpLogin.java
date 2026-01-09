/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.ws.rs.core.Response;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.peticion.LoginColaboradorPeticion;
import pojo.respuesta.Mensaje;

/**
 *
 * @author ian_e
 */
public class ImpLogin {
    public static Response validarSesionColaborador(LoginColaboradorPeticion login){
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        String mensaje = "";
        if(conexionBD != null){
            try{
                HashMap<String, Object> parametros = new LinkedHashMap<>();
                parametros.put("numPersonal",login.getNumPersonal());
                parametros.put("password",login.getPassword());
                Colaborador colaborador = conexionBD.selectOne("sesion.loginColaborador",parametros);
                if(colaborador != null){
                    return Mensaje.correcto("Credenciales correctas del colaborador "+colaborador.getNombre(), colaborador);
                }else{
                    return Mensaje.incorrecto(400, "No. personal y/o password incorrectos", login);
                }
            }catch(Exception e){
                e.printStackTrace();
                return Mensaje.incorrecto(500, e.getMessage(), e.getStackTrace());
            } finally {
                conexionBD.close();
            }
        }
        return Mensaje.incorrecto(500, "Por el momento no se puede consultar la informacion", null);
    }
    
        
    
}
