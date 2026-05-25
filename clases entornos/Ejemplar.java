package biblioteca;

public class Ejemplar {
    private int idEjemplar;
    private boolean disponible;

    public Ejemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
        this.disponible = true;
    }

    public int getIdEjemplar() { return idEjemplar; }
    public void setIdEjemplar(int idEjemplar) { this.idEjemplar = idEjemplar; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}