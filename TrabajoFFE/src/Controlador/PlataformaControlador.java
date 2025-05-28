package Controlador;

import BaseDeDatos.PlataformaDAO;
import Modelo.Plataforma;
import java.util.List;

public class PlataformaControlador {
    private PlataformaDAO plataformaDAO;

    public PlataformaControlador() {
        this.plataformaDAO = new PlataformaDAO();
    }

    // Método original (para la GUI)
    public boolean agregarPlataforma(String nombre, String paisOrigen) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre de la plataforma no puede estar vacío.");
            return false;
        }
        Plataforma nuevaPlataforma = new Plataforma(nombre.trim(), paisOrigen != null ? paisOrigen.trim() : "");
        return plataformaDAO.insertarPlataforma(nuevaPlataforma);
    }

    // NUEVO MÉTODO AGREGAR PLATAFORMA (para los tests y una API más limpia)
    public boolean agregarPlataforma(Plataforma plataforma) {
        if (plataforma == null || plataforma.getNombre() == null || plataforma.getNombre().trim().isEmpty()) {
            System.err.println("La plataforma o su nombre no pueden ser nulos o vacíos.");
            return false;
        }
        return plataformaDAO.insertarPlataforma(plataforma);
    }

    public boolean actualizarPlataforma(int id, String nombre, String paisOrigen) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre de la plataforma no puede estar vacío.");
            return false;
        }
        Plataforma plataformaActualizada = new Plataforma(id, nombre.trim(), paisOrigen != null ? paisOrigen.trim() : "");
        return plataformaDAO.actualizarPlataforma(plataformaActualizada);
    }

    // ... el resto de tus métodos ...
    public boolean eliminarPlataforma(int id) {
        return plataformaDAO.eliminarPlataforma(id);
    }

    public Plataforma obtenerPlataformaPorId(int id) {
        return plataformaDAO.obtenerPlataformaPorId(id);
    }

    public List<Plataforma> obtenerTodasLasPlataformas() {
        return plataformaDAO.obtenerTodasLasPlataformas();
    }
}