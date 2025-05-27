package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane; // Para mostrar mensajes de error

public class ConexionBBDD {

    // Configura estos valores según tu instalación de Oracle
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1"; // O tu SID, por ejemplo, ORCL
    private static final String USER = "alumno"; // Usuario con permisos para crear tablas (Ej: System, un usuario DBA o uno con CREATE TABLE)
    private static final String PASSWORD = "alumno"; // Contraseña de tu usuario Oracle

    private static Connection connection = null;

    /**
     * Establece y devuelve una conexión a la base de datos Oracle.
     * Si las tablas no existen, las crea automáticamente.
     * @return Una conexión a la base de datos.
     * @throws SQLException Si ocurre un error al conectar o crear tablas.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver JDBC de Oracle
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false); // Deshabilitar auto-commit para gestionar transacciones manualmente
                System.out.println("Conexión a la base de datos establecida.");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error: Driver JDBC de Oracle no encontrado.\n" + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                throw new SQLException("Driver JDBC de Oracle no encontrado.", e);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos o al crear tablas:\n" + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                throw e; // Relanzar la excepción para que el llamador pueda manejarla
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