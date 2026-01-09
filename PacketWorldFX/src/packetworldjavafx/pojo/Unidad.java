package packetworldjavafx.pojo;

/**
 *
 * @author ian_e
 */
public class Unidad {
    
    private String idInterno;

    private String marca;
    
    private String modelo;

    private Integer anio;

    private String vin;

    private Integer tipoPropulsionIdRef;

    private Integer estadoUnidadIdRef;

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