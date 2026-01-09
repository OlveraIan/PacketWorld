/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import servicio.ImpEnvio;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.InfoEnvio;
import pojo.respuesta.Mensaje;

/**
 *
 * @author DIANA
 */
@Path("envios")
public class WSEnvios {

    @Path("probar-conexion")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public boolean probarConexion() {
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        return conexion != null;
    }

    @Path("consultarEnvio")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public InfoEnvio consultarEnvio(@QueryParam("numGuia") Integer numGuia) {
        if (numGuia != null) {
            return ImpEnvio.consultarEnvio(numGuia);

        } else {
            throw new BadRequestException("El número de envío no es válido.");
        }
    }

    @Path("consultarEnvios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> getAllColaboradores() {
        return ImpEnvio.consultarEnvios();
    }

    @Path("registrarEnvio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarEnvio(String jsonEnvio) {

        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(jsonEnvio, Envio.class);

            return ImpEnvio.registrarEnvio(envio);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

    }

    @Path("actualizarEnvio")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarEnvio(String jsonEnvio) {
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(jsonEnvio, Envio.class);
            return ImpEnvio.actualizarEnvio(envio);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }

}
