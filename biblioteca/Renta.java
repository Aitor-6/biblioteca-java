package biblioteca;

import java.util.Date;

public class Renta {
    private Date fechaInicio;
    private Date fechaFin;
    private Socio socio;
    private Ejemplar ejemplar;

    public Renta(Date fechaInicio, Socio socio, Ejemplar ejemplar) {
        this.fechaInicio = fechaInicio;
        this.socio = socio;
        this.ejemplar = ejemplar;
    }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }

    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }
}