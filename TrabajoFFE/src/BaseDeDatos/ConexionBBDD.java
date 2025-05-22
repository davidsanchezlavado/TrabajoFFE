package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBBDD {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xepdb1";
    private static final String USUARIO = "alumno";
    private static final String CONTRASENA = "alumno";

    public static Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
