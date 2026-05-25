package biblioteca;

public class Libro {
    private int    id;
    private String titulo;
    private String autor;
    private int    anio;

    public Libro(int id, String titulo, String autor, int anio) {
        this.id     = id;
        this.titulo = titulo;
        this.autor  = autor;
        this.anio   = anio;
    }

    public int    getId()     { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor()  { return autor; }
    public int    getAnio()   { return anio; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor)   { this.autor  = autor; }
    public void setAnio(int anio)        { this.anio   = anio; }

    @Override
    public String toString() {
        return String.format("  ID: %d | Título: %s | Autor: %s | Año: %d",
                id, titulo, autor, anio);
    }
}
