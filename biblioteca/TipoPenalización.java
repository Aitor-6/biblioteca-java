package biblioteca;

public class TipoPenalizacion {
    private String tipo;
    private double importe;

    public TipoPenalizacion(String tipo, double importe) {
        this.tipo = tipo;
        this.importe = importe;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }
}