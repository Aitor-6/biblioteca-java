package biblioteca;

public class Usuario {
    private int    id;
    private String usuario;
    private String contrasena;

    public Usuario(int id, String usuario, String contrasena) {
        this.id         = id;
        this.usuario    = usuario;
        this.contrasena = contrasena;
    }

    public int    getId()         { return id; }
    public String getUsuario()    { return usuario; }
    public String getContrasena() { return contrasena; }

    public void setUsuario(String usuario)       { this.usuario    = usuario; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return String.format("  ID: %d | Usuario: %s", id, usuario);
    }
}
