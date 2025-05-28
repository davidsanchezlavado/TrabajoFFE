package Tests;

import Controlador.PlataformaControlador;
import Controlador.SerieControlador;
import Modelo.Plataforma;
import Modelo.Serie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import BaseDeDatos.ConexionBBDD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerieControladorTests {

    private SerieControlador serieControlador;
    private PlataformaControlador plataformaControlador;
    private Connection testConnection;

    private Plataforma plataformaDePrueba;

    @BeforeEach
    void setUp() throws SQLException {
        System.out.println("DEBUG: Iniciando setUp para SerieControladorTests...");
        testConnection = ConexionBBDD.getConnection();
        testConnection.setAutoCommit(false); // Deshabilitar autocommit para poder hacer rollback

        plataformaControlador = new PlataformaControlador();
        serieControlador = new SerieControlador();

        try (Statement stmt = testConnection.createStatement()) {
            stmt.executeUpdate("DELETE FROM SERIE");
            stmt.executeUpdate("DELETE FROM PLATAFORMA");
            System.out.println("DEBUG: Tablas Serie y Plataforma limpiadas antes de la prueba.");

            // Creamos una plataforma de prueba usando el método del controlador que toma String, String
            // Esto es crucial para satisfacer la clave foránea
            plataformaControlador.agregarPlataforma("Plataforma Test Series", "País Test");
            
            // Obtenemos la plataforma recién creada para tener su ID autogenerado real de la Base De Datos
            List<Plataforma> plataformas = plataformaControlador.obtenerTodasLasPlataformas();
            plataformaDePrueba = plataformas.stream()
                                            .filter(pl -> pl.getNombre().equals("Plataforma Test Series"))
                                            .findFirst()
                                            .orElseThrow(() -> new RuntimeException("No se pudo crear la plataforma de prueba para series."));
            System.out.println("DEBUG: Plataforma de prueba para series creada con ID: " + plataformaDePrueba.getId());

        } catch (SQLException e) {
            System.err.println("ERROR en setUp de SerieControladorTests: " + e.getMessage());
            throw e; // Relanzar la excepción para que JUnit la capture
        }
        System.out.println("DEBUG: setUp de SerieControladorTests finalizado.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("DEBUG: Iniciando tearDown para SerieControladorTests...");
        try {
            if (testConnection != null && !testConnection.isClosed()) {
                testConnection.rollback();
                testConnection.close();
                System.out.println("DEBUG: Rollback y conexión cerrada en tearDown.");
            }
        } catch (SQLException e) {
            System.err.println("Error al realizar rollback o cerrar conexión en tearDown: " + e.getMessage());
        }
        System.out.println("DEBUG: tearDown de SerieControladorTests finalizado.");
    }

    @Test
    void testAgregarSerieExitosa() {
        System.out.println("DEBUG: Ejecutando testAgregarSerieExitosa...");
        boolean resultado = serieControlador.agregarSerie(
            "Serie Test 1", "Genero Test", 1, 2020, plataformaDePrueba.getId());

        assertTrue(resultado, "Se esperaba que la serie se agregara exitosamente.");

        // Verificar que realmente se agregó en la base de datos
        List<Serie> series = serieControlador.obtenerTodasLasSeries();
        assertTrue(series.stream().anyMatch(s -> s.getTitulo().equals("Serie Test 1")),
                   "La serie 'Serie Test 1' debería existir en la base de datos.");
        System.out.println("DEBUG: testAgregarSerieExitosa finalizado.");
    }

    @Test
    void testObtenerTodasLasSeries() {
        System.out.println("DEBUG: Ejecutando testObtenerTodasLasSeries...");
        // Agregamos algunas series para probar la obtención
        serieControlador.agregarSerie("Serie Test 2", "Genero Test", 2, 2021, plataformaDePrueba.getId());
        serieControlador.agregarSerie("Serie Test 3", "Genero Test", 3, 2022, plataformaDePrueba.getId());

        List<Serie> resultado = serieControlador.obtenerTodasLasSeries();

        assertNotNull(resultado, "La lista de series no debería ser nula.");
        assertTrue(resultado.size() >= 2, "Se esperaban al menos 2 series.");
        System.out.println("DEBUG: testObtenerTodasLasSeries finalizado.");
    }

    @Test
    void testActualizarSerieExitosa() {
        System.out.println("DEBUG: Ejecutando testActualizarSerieExitosa...");
        // Primero agregamos una serie para poder actualizarla
        serieControlador.agregarSerie("Serie Antigua", "Genero Antiguo", 1, 2000, plataformaDePrueba.getId());
        List<Serie> series = serieControlador.obtenerTodasLasSeries();
        Serie serieAActualizar = series.stream()
                                      .filter(s -> s.getTitulo().equals("Serie Antigua"))
                                      .findFirst()
                                      .orElse(null);
        assertNotNull(serieAActualizar, "La serie a actualizar no debe ser nula.");

        // ** CAMBIO AQUI: Llamada al método actualizarSerie(int, String, String, int, int, int) **
        boolean resultado = serieControlador.actualizarSerie(
            serieAActualizar.getId(),
            "Serie Nueva",
            "Genero Nuevo",
            10,
            2025,
            plataformaDePrueba.getId()
        );

        assertTrue(resultado, "Se esperaba que la serie se actualizara exitosamente.");

        // Verificar que realmente se actualizó en la base de datos
        Serie serieActualizada = serieControlador.obtenerSeriePorId(serieAActualizar.getId());
        assertNotNull(serieActualizada);
        assertEquals("Serie Nueva", serieActualizada.getTitulo());
        assertEquals(10, serieActualizada.getNumTemporadas());
        assertEquals(2025, serieActualizada.getAnoLanzamiento());
        // También verifica que la plataforma sigue siendo la misma si no se ha cambiado
        assertEquals(plataformaDePrueba.getId(), serieActualizada.getPlataforma().getId());
        System.out.println("DEBUG: testActualizarSerieExitosa finalizado.");
    }

    @Test
    void testEliminarSerieExitosa() {
        System.out.println("DEBUG: Ejecutando testEliminarSerieExitosa...");
        // Agregamos una serie para poder eliminarla
        serieControlador.agregarSerie("Serie a Eliminar", "Genero X", 1, 2010, plataformaDePrueba.getId());
        List<Serie> seriesAntes = serieControlador.obtenerTodasLasSeries();
        Serie serieAEliminar = seriesAntes.stream()
                                        .filter(s -> s.getTitulo().equals("Serie a Eliminar"))
                                        .findFirst()
                                        .orElse(null);
        assertNotNull(serieAEliminar, "La serie a eliminar no debe ser nula.");

        boolean resultado = serieControlador.eliminarSerie(serieAEliminar.getId());

        assertTrue(resultado, "Se esperaba que la serie se eliminara exitosamente.");

        // Verificar que realmente se eliminó de la base de datos
        List<Serie> seriesDespues = serieControlador.obtenerTodasLasSeries();
        assertFalse(seriesDespues.stream().anyMatch(s -> s.getId() == serieAEliminar.getId()),
                    "La serie debería haber sido eliminada.");
        System.out.println("DEBUG: testEliminarSerieExitosa finalizado.");
    }

    @Test
    void testBuscarSeriesConFiltros() {
        System.out.println("DEBUG: Ejecutando testBuscarSeriesConFiltros...");
        // Agregamos algunas series para buscar
        serieControlador.agregarSerie("Busqueda Exacta", "Accion", 5, 2023, plataformaDePrueba.getId());
        serieControlador.agregarSerie("Otra Serie", "Drama", 2, 2021, plataformaDePrueba.getId());

        List<Serie> resultado = serieControlador.buscarSeries("Busqueda Exacta", "Accion", plataformaDePrueba.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Busqueda Exacta", resultado.get(0).getTitulo());
        assertEquals("Accion", resultado.get(0).getGenero());
        assertEquals(plataformaDePrueba.getId(), resultado.get(0).getPlataforma().getId());
        System.out.println("DEBUG: testBuscarSeriesConFiltros finalizado.");
    }
}