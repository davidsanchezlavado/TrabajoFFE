// src/BaseDeDatos/PlataformaDAO.java
package BaseDeDatos;

import Modelo.Plataforma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlataformaDAO {

    public boolean insertarPlataforma(Plataforma plataforma) {
        String sql = "INSERT INTO PLATAFORMA (NOMBRE, PAIS_ORIGEN) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql, new String[]{"ID"}); // Para recuperar el ID generado
            pstmt.setString(1, plataforma.getNombre());
            pstmt.setString(2, plataforma.getPaisOrigen());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                // Recuperar el ID generado automáticamente
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    plataforma.setId(rs.getInt(1)); // Asignar el ID a la plataforma
                }
                conn.commit(); // Confirmar la transacción
                return true;
            } else {
                conn.rollback(); // Deshacer si no se insertó ninguna fila
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar plataforma: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
            // No cerramos la conexión aquí, se cierra en Main o al finalizar la app
        }
    }

    public boolean actualizarPlataforma(Plataforma plataforma) {
        String sql = "UPDATE PLATAFORMA SET NOMBRE = ?, PAIS_ORIGEN = ? WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plataforma.getNombre());
            pstmt.setString(2, plataforma.getPaisOrigen());
            pstmt.setInt(3, plataforma.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar plataforma: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean eliminarPlataforma(int id) {
        String sql = "DELETE FROM PLATAFORMA WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar plataforma: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

    public Plataforma obtenerPlataformaPorId(int id) {
        String sql = "SELECT ID, NOMBRE, PAIS_ORIGEN FROM PLATAFORMA WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Plataforma plataforma = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                plataforma = new Plataforma();
                plataforma.setId(rs.getInt("ID"));
                plataforma.setNombre(rs.getString("NOMBRE"));
                plataforma.setPaisOrigen(rs.getString("PAIS_ORIGEN"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener plataforma por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return plataforma;
    }

    public List<Plataforma> obtenerTodasLasPlataformas() {
        List<Plataforma> plataformas = new ArrayList<>();
        String sql = "SELECT ID, NOMBRE, PAIS_ORIGEN FROM PLATAFORMA ORDER BY NOMBRE";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBBDD.getConnection(); // Obtiene la conexión
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql); // Ejecuta la consulta

            while (rs.next()) { // Itera sobre los resultados
                Plataforma plataforma = new Plataforma();
                plataforma.setId(rs.getInt("ID"));
                plataforma.setNombre(rs.getString("NOMBRE"));
                plataforma.setPaisOrigen(rs.getString("PAIS_ORIGEN"));
                plataformas.add(plataforma);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las plataformas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return plataformas;
    }
}