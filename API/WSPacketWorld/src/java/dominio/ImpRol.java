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
import pojo.Rol;

/**
 *
 * @author DIANA
 */
public class ImpRol {
    public static List <Rol> obtenerRol() {
     List<Rol> lista = new ArrayList();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            HashMap<String, String> parametros = new LinkedHashMap<>();
          lista = conexionBD.selectList("Rol.obtenerRol", parametros);
 
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return lista;
    }
    
}
