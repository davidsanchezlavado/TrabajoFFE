package Tests;

import Controlador.PlataformaControlador;
import Modelo.Plataforma;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import BaseDeDatos.ConexionBBDD;
import BaseDeDatos.PlataformaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlataformaControladorTests {

    private PlataformaControlador plataformaControlador;
    private PlataformaDAO plataformaDAO; 
    private Connection testConnection; 

    @BeforeEach
    void setUp() throws SQLException {
        // Obtenemos una conexión de prueba para cada método de test
        testConnection = ConexionBBDD.getConnection();
        // Configuramos la conexión para que no haga autocommit, así podemos hacer rollback
        testConnection.setAutoCommit(false);

        // Creamos una instancia real del controlador y del DAO
        plataformaDAO = new PlataformaDAO(); 
        plataformaControlador = new PlataformaControlador(); 
        // Limpiamos la tabla antes de cada prueba
        try (Statement stmt = testConnection.createStatement()) {
            stmt.executeUpdate("DELETE FROM SERIE");
            stmt.executeUpdate("DELETE FROM PLATAFORMA");
            System.out.println("DEBUG: Tablas Plataforma y Serie limpiadas antes de la prueba.");
        }
    }

    @AfterEach
    void tearDown() {
        // Hacer rollback de todos los cambios al finalizar cada prueba
        try {
            if (testConnection != null && !testConnection.isClosed()) {
                testConnection.rollback(); // Deshacer los cambios
                testConnection.close();
                System.out.println("DEBUG: Rollback y conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al realizar rollback o cerrar conexión en tearDown: " + e.getMessage());
        }
    }

    @Test
    void testAgregarPlataformaExitosa() {
        Plataforma p = new Plataforma("Netflix Prueba", "EEUU");
        boolean resultado = plataformaControlador.agregarPlataforma(p);

        assertTrue(resultado, "Se esperaba que la plataforma se agregara exitosamente.");

        // Verificar que realmente se agregó en la base de datos
        List<Plataforma> plataformas = plataformaControlador.obtenerTodasLasPlataformas();
        assertTrue(plataformas.stream().anyMatch(pl -> pl.getNombre().equals("Netflix Prueba")),
                   "La plataforma 'Netflix Prueba' debería existir en la base de datos.");
    }

    @Test
    void testObtenerTodasLasPlataformas() {
        // Agregamos algunas plataformas para probar la obtención
        plataformaControlador.agregarPlataforma(new Plataforma("HBO Prueba", "EEUU"));
        plataformaControlador.agregarPlataforma(new Plataforma("Amazon Prueba", "EEUU"));

        List<Plataforma> resultado = plataformaControlador.obtenerTodasLasPlataformas();

        assertNotNull(resultado, "La lista de plataformas no debería ser nula.");
        assertTrue(resultado.size() >= 2, "Se esperaban al menos 2 plataformas.");
    }

    @Test
    void testActualizarPlataformaExitosa() {
        // Primero agregamos una plataforma para poder actualizarla
        Plataforma pInicial = new Plataforma("Plataforma Prueba Update", "Pais Inicial");
        plataformaControlador.agregarPlataforma(pInicial.getNombre(), pInicial.getPaisOrigen()); // Llamada al método de strings

        List<Plataforma> plataformas = plataformaControlador.obtenerTodasLasPlataformas();
        Plataforma plataformaAActualizar = plataformas.stream()
                                                    .filter(p -> p.getNombre().equals("Plataforma Prueba Update"))
                                                    .findFirst()
                                                    .orElse(null);
        assertNotNull(plataformaAActualizar, "La plataforma a actualizar no debe ser nula después de la inserción.");

        // Modificamos el nombre y país
        String nuevoNombre = "Plataforma Actualizada";
        String nuevoPais = "Pais Actualizado";

        // Llamamos al método actualizarPlataforma de PlataformaControlador
        boolean resultado = plataformaControlador.actualizarPlataforma(
            plataformaAActualizar.getId(), // Usamos el ID real de la DB
            nuevoNombre,
            nuevoPais
        );

        assertTrue(resultado, "Se esperaba que la plataforma se actualizara exitosamente.");

        // Verificar que realmente se actualizó en la base de datos
        Plataforma plataformaVerificada = plataformaControlador.obtenerPlataformaPorId(plataformaAActualizar.getId()); // Obtenerla por ID
        assertNotNull(plataformaVerificada);
        assertEquals(nuevoNombre, plataformaVerificada.getNombre());
        assertEquals(nuevoPais, plataformaVerificada.getPaisOrigen());
    }

    @Test
    void testEliminarPlataformaExitosa() {
        // Agregamos una plataforma para poder eliminarla
        plataformaControlador.agregarPlataforma(new Plataforma("Plataforma a Eliminar", "Pais X"));
        List<Plataforma> plataformasAntes = plataformaControlador.obtenerTodasLasPlataformas();
        Plataforma plataformaAEliminar = plataformasAntes.stream()
                                                        .filter(p -> p.getNombre().equals("Plataforma a Eliminar"))
                                                        .findFirst()
                                                        .orElse(null);
        assertNotNull(plataformaAEliminar, "La plataforma a eliminar no debe ser nula.");

        boolean resultado = plataformaControlador.eliminarPlataforma(plataformaAEliminar.getId());

        assertTrue(resultado, "Se esperaba que la plataforma se eliminara exitosamente.");

        // Verificar que realmente se eliminó de la base de datos
        List<Plataforma> plataformasDespues = plataformaControlador.obtenerTodasLasPlataformas();
        assertFalse(plataformasDespues.stream().anyMatch(p -> p.getId() == plataformaAEliminar.getId()),
                    "La plataforma debería haber sido eliminada.");
    }
}