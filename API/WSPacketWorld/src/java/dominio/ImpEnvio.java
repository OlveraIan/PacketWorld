/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.InfoEnvio;
import pojo.Mensaje;

/**
 *
 * @author DIANA
 */
public class ImpEnvio {

    public static InfoEnvio consultarEnvio(Integer numGuia) {

        InfoEnvio respuesta = new InfoEnvio();
        SqlSession conexionBD = mybatis.MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {

            try {
                HashMap<String, Integer> parametros = new LinkedHashMap<>();
                parametros.put("numGuia", numGuia);

                Envio envio = conexionBD.selectOne("envios.consultarEnvio", parametros);

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

    public static List<Envio> consultarEnvios() {
        SqlSession conexionBD = mybatis.MyBatisUtil.obtenerConexion();
        List<Envio> envios = null;

        try {
            envios = conexionBD.selectList("envios.consultarEnvios");
        } catch (Exception e) {

        } finally {
            conexionBD.close();
        }

        return envios;
    }

    public static Mensaje registrarEnvio(Envio envio) {

        Mensaje msj = new Mensaje();

        SqlSession conexionBD = mybatis.MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {

            try {
                int resultado = conexionBD.insert("envios.registrarEnvio", envio);
                conexionBD.commit();
                if (resultado > 0) {
                    msj.setError(false);
                    msj.setMensaje("Envío registrado con éxito");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo añadir el envío");
                }

            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }

        } else {
            msj.setError(true);
            msj.setMensaje("Error: No hubo conexíon.");
        }
        return msj;
    }

    public static Mensaje actualizarEnvio(Envio envio) {
        Mensaje msj = new Mensaje();

        SqlSession conexionBD = mybatis.MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {

                int resultado = conexionBD.update("envios.actualizarEnvio", envio);
                conexionBD.commit();
                if (resultado > 0) {
                    msj.setError(false);
                    msj.setMensaje("Envio actualizado con éxito");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo actualizar el envío");
                }

            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Error: No hubo conexión.");
        }

        return msj;

    }

}

