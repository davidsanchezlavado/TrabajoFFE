package Controlador;

import BaseDeDatos.SerieDAO;
import Modelo.Serie;
import Modelo.Plataforma; // Si se necesita para alguna validación o visualización
import java.util.List;

public class SerieControlador {
    private SerieDAO serieDAO;

    public SerieControlador() {
        this.serieDAO = new SerieDAO();
    }

    public boolean agregarSerie(String titulo, String genero, int numTemporadas, int anoLanzamiento, int idPlataforma) {
        if (titulo == null || titulo.trim().isEmpty()) {
            System.err.println("El título de la serie no puede estar vacío.");
            return false;
        }
        if (numTemporadas <= 0) {
            System.err.println("El número de temporadas debe ser un valor positivo.");
            return false;
        }
        if (anoLanzamiento <= 1900 || anoLanzamiento > 2100) { // Un rango razonable
            System.err.println("El año de lanzamiento no parece válido.");
            return false;
        }

        Serie nuevaSerie = new Serie(titulo.trim(),
                                     genero != null ? genero.trim() : "",
                                     numTemporadas,
                                     anoLanzamiento,
                                     idPlataforma);
        return serieDAO.insertarSerie(nuevaSerie);
    }

    public boolean actualizarSerie(int id, String titulo, String genero, int numTemporadas, int anoLanzamiento, int idPlataforma) {
        if (titulo == null || titulo.trim().isEmpty()) {
            System.err.println("El título de la serie no puede estar vacío.");
            return false;
        }
        if (numTemporadas <= 0) {
            System.err.println("El número de temporadas debe ser un valor positivo.");
            return false;
        }
        if (anoLanzamiento <= 1900 || anoLanzamiento > 2100) {
            System.err.println("El año de lanzamiento no parece válido.");
            return false;
        }

        Serie serieActualizada = new Serie(id,
                                           titulo.trim(),
                                           genero != null ? genero.trim() : "",
                                           numTemporadas,
                                           anoLanzamiento,
                                           idPlataforma);
        return serieDAO.actualizarSerie(serieActualizada);
    }

    public boolean eliminarSerie(int id) {
        return serieDAO.eliminarSerie(id);
    }

    public Serie obtenerSeriePorId(int id) {
        return serieDAO.obtenerSeriePorId(id);
    }

    public List<Serie> obtenerTodasLasSeries() {
        return serieDAO.obtenerTodasLasSeries();
    }

    public List<Serie> buscarSeries(String tituloFragmento, String genero, int idPlataforma) {
        return serieDAO.buscarSeries(tituloFragmento, genero, idPlataforma);
    }
}