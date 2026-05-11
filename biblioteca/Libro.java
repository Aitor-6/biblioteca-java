package biblioteca;

public class Libro {
    private String nombre;
    private String ISBN;
    private String descripcion;
    private String imagen;
    private int codLibro;
    private int numEjemplares;

    public Libro(String nombre, String ISBN, int codLibro) {
        this.nombre = nombre;
        this.ISBN = ISBN;
        this.codLibro = codLibro;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getCodLibro() { return codLibro; }
    public void setCodLibro(int codLibro) { this.codLibro = codLibro; }

    public int getNumEjemplares() { return numEjemplares; }
    public void setNumEjemplares(int numEjemplares) { this.numEjemplares = numEjemplares; }
}