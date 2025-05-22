package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBBDD {
    private static Connection conexion;

    public static Connection conectar() {
        if (conexion == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conexion = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xepdb1", "alumno", "alumno");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }
}
