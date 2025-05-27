package BaseDeDatos;

import Modelo.Serie;
import Modelo.Plataforma; // Necesario para cargar el objeto Plataforma dentro de Serie
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    private PlataformaDAO plataformaDAO; // Para obtener los objetos Plataforma

    public SerieDAO() {
        this.plataformaDAO = new PlataformaDAO();
    }

    /**
     * El ID se generará automáticamente por la secuencia.
     */
    public boolean insertarSerie(Serie serie) {
        String sql = "INSERT INTO SERIE (TITULO, GENERO, NUM_TEMPORADAS, ANO_LANZAMIENTO, ID_PLATAFORMA) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql, new String[]{"ID"}); // Para recuperar el ID generado
            pstmt.setString(1, serie.getTitulo());
            pstmt.setString(2, serie.getGenero());
            pstmt.setInt(3, serie.getNumTemporadas());
            pstmt.setInt(4, serie.getAnoLanzamiento());
            // Manejar caso donde ID_PLATAFORMA podría ser 0 o no válido
            if (serie.getIdPlataforma() > 0) {
                pstmt.setInt(5, serie.getIdPlataforma());
            } else {
                pstmt.setNull(5, java.sql.Types.NUMERIC); // O establecer a NULL si no hay plataforma
            }

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    serie.setId(rs.getInt(1));
                }
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar serie: " + e.getMessage());
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
    
    public boolean actualizarSerie(Serie serie) {
        String sql = "UPDATE SERIE SET TITULO = ?, GENERO = ?, NUM_TEMPORADAS = ?, ANO_LANZAMIENTO = ?, ID_PLATAFORMA = ? WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, serie.getTitulo());
            pstmt.setString(2, serie.getGenero());
            pstmt.setInt(3, serie.getNumTemporadas());
            pstmt.setInt(4, serie.getAnoLanzamiento());
            if (serie.getIdPlataforma() > 0) {
                pstmt.setInt(5, serie.getIdPlataforma());
            } else {
                pstmt.setNull(5, java.sql.Types.NUMERIC);
            }
            pstmt.setInt(6, serie.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar serie: " + e.getMessage());
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
    
    public boolean eliminarSerie(int id) {
        String sql = "DELETE FROM SERIE WHERE ID = ?";
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
            System.err.println("Error al eliminar serie: " + e.getMessage());
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

    public Serie obtenerSeriePorId(int id) {
        String sql = "SELECT ID, TITULO, GENERO, NUM_TEMPORADAS, ANO_LANZAMIENTO, ID_PLATAFORMA FROM SERIE WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Serie serie = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                serie = new Serie();
                serie.setId(rs.getInt("ID"));
                serie.setTitulo(rs.getString("TITULO"));
                serie.setGenero(rs.getString("GENERO"));
                serie.setNumTemporadas(rs.getInt("NUM_TEMPORADAS"));
                serie.setAnoLanzamiento(rs.getInt("ANO_LANZAMIENTO"));
                int idPlataforma = rs.getInt("ID_PLATAFORMA");
                serie.setIdPlataforma(idPlataforma);

                // Cargar el objeto Plataforma asociado
                if (idPlataforma > 0) {
                    Plataforma plataforma = plataformaDAO.obtenerPlataformaPorId(idPlataforma);
                    serie.setPlataforma(plataforma);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener serie por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return serie;
    }

    public List<Serie> obtenerTodasLasSeries() {
        List<Serie> series = new ArrayList<>();
        String sql = "SELECT ID, TITULO, GENERO, NUM_TEMPORADAS, ANO_LANZAMIENTO, ID_PLATAFORMA FROM SERIE ORDER BY TITULO";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBBDD.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Serie serie = new Serie();
                serie.setId(rs.getInt("ID"));
                serie.setTitulo(rs.getString("TITULO"));
                serie.setGenero(rs.getString("GENERO"));
                serie.setNumTemporadas(rs.getInt("NUM_TEMPORADAS"));
                serie.setAnoLanzamiento(rs.getInt("ANO_LANZAMIENTO"));
                int idPlataforma = rs.getInt("ID_PLATAFORMA");
                serie.setIdPlataforma(idPlataforma);

                // Cargar el objeto Plataforma asociado
                if (idPlataforma > 0) {
                    Plataforma plataforma = plataformaDAO.obtenerPlataformaPorId(idPlataforma);
                    serie.setPlataforma(plataforma);
                }
                series.add(serie);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las series: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return series;
    }

    /**
     * Busca series por título (parcial) o género (exacto).
     * @param tituloFragmento Título o parte del título a buscar.
     * @param genero Género exacto a buscar. Puede ser null o vacío para no filtrar por género.
     * @param idPlataforma ID de la plataforma a buscar. 0 o negativo para no filtrar por plataforma.
     * @return Lista de series que coinciden con los filtros.
     */
    public List<Serie> buscarSeries(String tituloFragmento, String genero, int idPlataforma) {
        List<Serie> series = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT ID, TITULO, GENERO, NUM_TEMPORADAS, ANO_LANZAMIENTO, ID_PLATAFORMA FROM SERIE WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tituloFragmento != null && !tituloFragmento.trim().isEmpty()) {
            sqlBuilder.append(" AND UPPER(TITULO) LIKE UPPER(?)");
            params.add("%" + tituloFragmento.trim() + "%");
        }
        if (genero != null && !genero.trim().isEmpty()) {
            sqlBuilder.append(" AND UPPER(GENERO) = UPPER(?)");
            params.add(genero.trim());
        }
        if (idPlataforma > 0) {
            sqlBuilder.append(" AND ID_PLATAFORMA = ?");
            params.add(idPlataforma);
        }

        sqlBuilder.append(" ORDER BY TITULO");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexionBBDD.getConnection();
            pstmt = conn.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Serie serie = new Serie();
                serie.setId(rs.getInt("ID"));
                serie.setTitulo(rs.getString("TITULO"));
                serie.setGenero(rs.getString("GENERO"));
                serie.setNumTemporadas(rs.getInt("NUM_TEMPORADAS"));
                serie.setAnoLanzamiento(rs.getInt("ANO_LANZAMIENTO"));
                int idPlataformaFK = rs.getInt("ID_PLATAFORMA");
                serie.setIdPlataforma(idPlataformaFK);

                if (idPlataformaFK > 0) {
                    Plataforma plataforma = plataformaDAO.obtenerPlataformaPorId(idPlataformaFK);
                    serie.setPlataforma(plataforma);
                }
                series.add(serie);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar series: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return series;
    }
}