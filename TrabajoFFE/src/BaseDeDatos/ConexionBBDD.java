package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane; // Para mostrar mensajes de error

public class ConexionBBDD {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1"; 
    private static final String USER = "alumno";
    private static final String PASSWORD = "alumno";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver JDBC de Oracle
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
                System.out.println("Conexión a la base de datos establecida.");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error: Driver JDBC de Oracle no encontrado.\n" + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                throw new SQLException("Driver JDBC de Oracle no encontrado.", e);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos o al crear tablas:\n" + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                throw e;
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión a la base de datos:\n" + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}