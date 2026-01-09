/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import pojo.respuesta.Mensaje;

/**
 *
 * @author ian_e
 */
public class ExceptionManager implements ExceptionMapper<ConstraintViolationException>{
    @Override
    public Response toResponse(ConstraintViolationException e) {
        String mensaje = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        return Mensaje.incorrecto(400, "Error de validacion: " + mensaje, null);
    }
    public static Response manejarErrorSql(PersistenceException e) {
        e.printStackTrace();
        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            SQLException sqlEx = (SQLException) cause;
            String sqlState = sqlEx.getSQLState();
            String mensaje = sqlEx.getMessage();
            switch (sqlState) {
                case "23000":
                    return Mensaje.incorrecto(409, mensaje, null);
                case "45000":
                    return Mensaje.incorrecto(409, mensaje, null);
                case "45001":
                    return Mensaje.incorrecto(400, mensaje, null);
                case "45002":
                    return Mensaje.incorrecto(404, mensaje, null);
            }
        }
        return Mensaje.incorrecto(
                500, 
                "Error interno en la base de datos", 
                cause != null 
                        ? cause.getMessage() : e.getMessage()
        );
    }
}
