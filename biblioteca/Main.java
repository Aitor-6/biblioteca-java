package biblioteca;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc         = new Scanner(System.in);
    private static final LibroDAO   libroDAO   = new LibroDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ══════════════════════════════════════════════════════════════════════════
    //  ENTRADA
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        cabecera("SISTEMA DE GESTIÓN DE BIBLIOTECA");
        boolean salir = false;
        while (!salir) {
            menuPrincipal();
            int op = leerInt();
            switch (op) {
                case 1 -> menuLibros();
                case 2 -> menuUsuarios();
                case 0 -> salir = true;
                default -> error("Opción no válida.");
            }
        }
        System.out.println("\n  Hasta luego.\n");
        sc.close();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENÚ PRINCIPAL
    // ══════════════════════════════════════════════════════════════════════════
    private static void menuPrincipal() {
        separador();
        System.out.println("  MENÚ PRINCIPAL");
        separador();
        System.out.println("  1. Gestionar Libros");
        System.out.println("  2. Gestionar Usuarios");
        System.out.println("  0. Salir");
        separador();
        System.out.print("  Elige una opción: ");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENÚ LIBROS
    // ══════════════════════════════════════════════════════════════════════════
    private static void menuLibros() {
        boolean volver = false;
        while (!volver) {
            separador();
            System.out.println("  LIBROS");
            separador();
            System.out.println("  1. Ver todos los libros");
            System.out.println("  2. Añadir libro");
            System.out.println("  3. Modificar libro");
            System.out.println("  4. Eliminar libro");
            System.out.println("  0. Volver");
            separador();
            System.out.print("  Elige una opción: ");
            int op = leerInt();
            switch (op) {
                case 1 -> verLibros();
                case 2 -> aniadirLibro();
                case 3 -> modificarLibro();
                case 4 -> eliminarLibro();
                case 0 -> volver = true;
                default -> error("Opción no válida.");
            }
        }
    }

    // ── Ver todos los libros ──────────────────────────────────────────────────
    private static void verLibros() {
        try {
            List<Libro> lista = libroDAO.listarTodos();
            separador();
            System.out.println("  LISTADO DE LIBROS (" + lista.size() + ")");
            separador();
            if (lista.isEmpty()) {
                System.out.println("  No hay libros registrados.");
            } else {
                for (Libro l : lista) System.out.println(l);
            }
        } catch (SQLException e) {
            error("Error al obtener libros: " + e.getMessage());
        }
    }

    // ── Añadir libro ──────────────────────────────────────────────────────────
    private static void aniadirLibro() {
        separador();
        System.out.println("  AÑADIR LIBRO");
        separador();

        System.out.print("  Título  : ");
        String titulo = leerTexto();
        if (titulo.isEmpty()) { error("El título no puede estar vacío."); return; }

        System.out.print("  Autor   : ");
        String autor = leerTexto();
        if (autor.isEmpty()) { error("El autor no puede estar vacío."); return; }

        System.out.print("  Año     : ");
        int anio = leerInt();
        if (anio <= 0) { error("Año no válido."); return; }

        try {
            if (libroDAO.insertar(titulo, autor, anio)) {
                ok("Libro añadido correctamente.");
            } else {
                error("No se pudo añadir el libro.");
            }
        } catch (SQLException e) {
            error("Error al añadir libro: " + e.getMessage());
        }
    }

    // ── Modificar libro (paso a paso) ─────────────────────────────────────────
    private static void modificarLibro() {
        verLibros();

        separador();
        System.out.print("  Introduce el ID del libro a modificar (0 para cancelar): ");
        int id = leerInt();
        if (id == 0) return;

        try {
            Libro libro = libroDAO.buscarPorId(id);
            if (libro == null) { error("No existe ningún libro con ese ID."); return; }

            separador();
            System.out.println("  MODIFICAR LIBRO  [deja en blanco para mantener el valor actual]");
            separador();

            // ── Título ──────────────────────────────────────────────────────
            System.out.println("  Título actual  : " + libro.getTitulo());
            System.out.print("  Nuevo título   : ");
            String nuevoTitulo = leerTexto();
            if (!nuevoTitulo.isEmpty()) libro.setTitulo(nuevoTitulo);

            // ── Autor ───────────────────────────────────────────────────────
            System.out.println("  Autor actual   : " + libro.getAutor());
            System.out.print("  Nuevo autor    : ");
            String nuevoAutor = leerTexto();
            if (!nuevoAutor.isEmpty()) libro.setAutor(nuevoAutor);

            // ── Año ─────────────────────────────────────────────────────────
            System.out.println("  Año actual     : " + libro.getAnio());
            System.out.print("  Nuevo año      : ");
            String inputAnio = leerTexto();
            if (!inputAnio.isEmpty()) {
                try {
                    libro.setAnio(Integer.parseInt(inputAnio.trim()));
                } catch (NumberFormatException e) {
                    error("Año no válido, se mantiene el valor anterior.");
                }
            }

            // ── Confirmación ────────────────────────────────────────────────
            separador();
            System.out.println("  RESUMEN DE CAMBIOS:");
            System.out.println(libro);
            separador();
            System.out.print("  ¿Confirmar cambios? (s/n): ");
            String conf = leerTexto().toLowerCase();
            if (conf.equals("s")) {
                if (libroDAO.actualizar(libro)) {
                    ok("Libro actualizado correctamente.");
                } else {
                    error("No se pudo actualizar el libro.");
                }
            } else {
                System.out.println("  Operación cancelada.");
            }

        } catch (SQLException e) {
            error("Error al modificar libro: " + e.getMessage());
        }
    }

    // ── Eliminar libro ────────────────────────────────────────────────────────
    private static void eliminarLibro() {
        verLibros();

        separador();
        System.out.print("  Introduce el ID del libro a eliminar (0 para cancelar): ");
        int id = leerInt();
        if (id == 0) return;

        try {
            Libro libro = libroDAO.buscarPorId(id);
            if (libro == null) { error("No existe ningún libro con ese ID."); return; }

            separador();
            System.out.println("  Vas a eliminar:");
            System.out.println(libro);
            separador();
            System.out.print("  ¿Confirmar eliminación? (s/n): ");
            String conf = leerTexto().toLowerCase();
            if (conf.equals("s")) {
                if (libroDAO.eliminar(id)) {
                    ok("Libro eliminado correctamente.");
                } else {
                    error("No se pudo eliminar el libro.");
                }
            } else {
                System.out.println("  Operación cancelada.");
            }

        } catch (SQLException e) {
            error("Error al eliminar libro: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MENÚ USUARIOS
    // ══════════════════════════════════════════════════════════════════════════
    private static void menuUsuarios() {
        boolean volver = false;
        while (!volver) {
            separador();
            System.out.println("  USUARIOS");
            separador();
            System.out.println("  1. Ver todos los usuarios");
            System.out.println("  2. Añadir usuario");
            System.out.println("  3. Modificar usuario");
            System.out.println("  4. Eliminar usuario");
            System.out.println("  0. Volver");
            separador();
            System.out.print("  Elige una opción: ");
            int op = leerInt();
            switch (op) {
                case 1 -> verUsuarios();
                case 2 -> aniadirUsuario();
                case 3 -> modificarUsuario();
                case 4 -> eliminarUsuario();
                case 0 -> volver = true;
                default -> error("Opción no válida.");
            }
        }
    }

    // ── Ver todos los usuarios ────────────────────────────────────────────────
    private static void verUsuarios() {
        try {
            List<Usuario> lista = usuarioDAO.listarTodos();
            separador();
            System.out.println("  LISTADO DE USUARIOS (" + lista.size() + ")");
            separador();
            if (lista.isEmpty()) {
                System.out.println("  No hay usuarios registrados.");
            } else {
                for (Usuario u : lista) System.out.println(u);
            }
        } catch (SQLException e) {
            error("Error al obtener usuarios: " + e.getMessage());
        }
    }

    // ── Añadir usuario ────────────────────────────────────────────────────────
    private static void aniadirUsuario() {
        separador();
        System.out.println("  AÑADIR USUARIO");
        separador();

        System.out.print("  Nombre de usuario : ");
        String usuario = leerTexto();
        if (usuario.isEmpty()) { error("El nombre de usuario no puede estar vacío."); return; }

        System.out.print("  Contraseña        : ");
        String contrasena = leerTexto();
        if (contrasena.isEmpty()) { error("La contraseña no puede estar vacía."); return; }

        try {
            if (usuarioDAO.insertar(usuario, contrasena)) {
                ok("Usuario añadido correctamente.");
            } else {
                error("No se pudo añadir el usuario.");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                error("Ya existe un usuario con ese nombre.");
            } else {
                error("Error al añadir usuario: " + e.getMessage());
            }
        }
    }

    // ── Modificar usuario (paso a paso) ───────────────────────────────────────
    private static void modificarUsuario() {
        verUsuarios();

        separador();
        System.out.print("  Introduce el ID del usuario a modificar (0 para cancelar): ");
        int id = leerInt();
        if (id == 0) return;

        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) { error("No existe ningún usuario con ese ID."); return; }

            separador();
            System.out.println("  MODIFICAR USUARIO  [deja en blanco para mantener el valor actual]");
            separador();

            // ── Nombre de usuario ───────────────────────────────────────────
            System.out.println("  Usuario actual         : " + usuario.getUsuario());
            System.out.print("  Nuevo nombre de usuario: ");
            String nuevoUsuario = leerTexto();
            if (!nuevoUsuario.isEmpty()) usuario.setUsuario(nuevoUsuario);

            // ── Contraseña ──────────────────────────────────────────────────
            System.out.println("  (La contraseña actual está oculta por seguridad)");
            System.out.print("  Nueva contraseña       : ");
            String nuevaContrasena = leerTexto();
            if (!nuevaContrasena.isEmpty()) usuario.setContrasena(nuevaContrasena);

            // ── Confirmación ────────────────────────────────────────────────
            separador();
            System.out.println("  RESUMEN DE CAMBIOS:");
            System.out.println(usuario);
            separador();
            System.out.print("  ¿Confirmar cambios? (s/n): ");
            String conf = leerTexto().toLowerCase();
            if (conf.equals("s")) {
                if (usuarioDAO.actualizar(usuario)) {
                    ok("Usuario actualizado correctamente.");
                } else {
                    error("No se pudo actualizar el usuario.");
                }
            } else {
                System.out.println("  Operación cancelada.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                error("Ya existe un usuario con ese nombre.");
            } else {
                error("Error al modificar usuario: " + e.getMessage());
            }
        }
    }

    // ── Eliminar usuario ──────────────────────────────────────────────────────
    private static void eliminarUsuario() {
        verUsuarios();

        separador();
        System.out.print("  Introduce el ID del usuario a eliminar (0 para cancelar): ");
        int id = leerInt();
        if (id == 0) return;

        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) { error("No existe ningún usuario con ese ID."); return; }

            separador();
            System.out.println("  Vas a eliminar:");
            System.out.println(usuario);
            separador();
            System.out.print("  ¿Confirmar eliminación? (s/n): ");
            String conf = leerTexto().toLowerCase();
            if (conf.equals("s")) {
                if (usuarioDAO.eliminar(id)) {
                    ok("Usuario eliminado correctamente.");
                } else {
                    error("No se pudo eliminar el usuario.");
                }
            } else {
                System.out.println("  Operación cancelada.");
            }

        } catch (SQLException e) {
            error("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  UTILIDADES
    // ══════════════════════════════════════════════════════════════════════════

    private static int leerInt() {
        try {
            String linea = sc.nextLine().trim();
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String leerTexto() {
        return sc.nextLine(); // Conserva espacios, permite vacío para "no cambiar"
    }

    private static void separador() {
        System.out.println("  ──────────────────────────────────────");
    }

    private static void cabecera(String texto) {
        System.out.println("\n  ══════════════════════════════════════");
        System.out.println("  " + texto);
        System.out.println("  ══════════════════════════════════════\n");
    }

    private static void ok(String msg) {
        System.out.println("\n  ✔ " + msg + "\n");
    }

    private static void error(String msg) {
        System.out.println("\n  ✘ " + msg + "\n");
    }
}
