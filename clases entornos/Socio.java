package biblioteca;

public class Socio {
    private int idSocio;
    private String nombre;
    private String email;

    public Socio(int idSocio, String nombre, String email) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.email = email;
    }

    public int getIdSocio() { return idSocio; }
    public void setIdSocio(int idSocio) { this.idSocio = idSocio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}