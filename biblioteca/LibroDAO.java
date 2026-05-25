package biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // ── Listar todos ──────────────────────────────────────────────────────────
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, año FROM libros ORDER BY id";
        try (Connection con = Conexion.conectar();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("año")
                ));
            }
        }
        return lista;
    }

    // ── Buscar por ID ─────────────────────────────────────────────────────────
    public Libro buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, titulo, autor, año FROM libros WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("año")
                    );
                }
            }
        }
        return null;
    }

    // ── Insertar ──────────────────────────────────────────────────────────────
    public boolean insertar(String titulo, String autor, int anio) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, año) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setInt(3, anio);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Actualizar ────────────────────────────────────────────────────────────
    public boolean actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, año = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setInt(3, libro.getAnio());
            ps.setInt(4, libro.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
