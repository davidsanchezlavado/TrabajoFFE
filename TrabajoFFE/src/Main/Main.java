package Main;

import BaseDeDatos.ConexionBBDD;
import Vista.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO APLICACIÓN ---"); 

        // Asegurarse de que la interfaz gráfica se ejecute
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Intentar establecer la conexión
                    ConexionBBDD.getConnection();
                    // Si la conexión es exitosa, mostrar la ventana principal
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "No se pudo iniciar la aplicación debido a un error de base de datos.\n" + e.getMessage(), "Error de Inicio", JOptionPane.ERROR_MESSAGE);
                    System.exit(1); // Salir de la aplicación si no se puede conectar a la BD
                }
            }
        });

        // Asegurarse de cerrar la conexión cuando la aplicación se cierre
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConexionBBDD.closeConnection();
        }));
    }
}