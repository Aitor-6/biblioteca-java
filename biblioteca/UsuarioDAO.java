package biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ── Listar todos ──────────────────────────────────────────────────────────
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, usuario, contraseña FROM usuarios ORDER BY id";
        try (Connection con = Conexion.conectar();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contraseña")
                ));
            }
        }
        return lista;
    }

    // ── Buscar por ID ─────────────────────────────────────────────────────────
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, usuario, contraseña FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("usuario"),
                            rs.getString("contraseña")
                    );
                }
            }
        }
        return null;
    }

    // ── Insertar ──────────────────────────────────────────────────────────────
    public boolean insertar(String usuario, String contrasena) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, contraseña) VALUES (?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Actualizar ────────────────────────────────────────────────────────────
    public boolean actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET usuario = ?, contraseña = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getContrasena());
            ps.setInt(3, u.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
