package pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ian_e
 */
public class Unidad {
    @NotNull(message = "El ID interno es obligatorio")
    private String idInterno;

    @NotNull(message = "La marca es obligatoria")
    private String marca;

    @NotNull(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    @NotNull(message = "El VIN es obligatorio")
    @Size(min = 17, max = 17, message = "El VIN debe tener exactamente 17 caracteres")
    private String vin;

    @NotNull(message = "El tipo de propulsión es obligatorio")
    private Integer tipoPropulsionIdRef;

    @NotNull(message = "El estado de la unidad es obligatorio")
    private Integer estadoUnidadIdRef;

    @Size(max = 20, message = "El número de licencia del conductor no debe exceder los 20 caracteres")
    private String conductorDesignado;
    
    private String tipoPropulsion;
    
    private String estadoActual;

    public Unidad() {
    }

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getTipoPropulsionIdRef() {
        return tipoPropulsionIdRef;
    }

    public void setTipoPropulsionIdRef(Integer tipoPropulsionIdRef) {
        this.tipoPropulsionIdRef = tipoPropulsionIdRef;
    }

    public Integer getEstadoUnidadIdRef() {
        return estadoUnidadIdRef;
    }

    public void setEstadoUnidadIdRef(Integer estadoUnidadIdRef) {
        this.estadoUnidadIdRef = estadoUnidadIdRef;
    }

    public String getConductorDesignado() {
        return conductorDesignado;
    }

    public void setConductorDesignado(String conductorDesignado) {
        this.conductorDesignado = conductorDesignado;
    }

    public String getTipoPropulsion() {
        return tipoPropulsion;
    }

    public void setTipoPropulsion(String tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
}