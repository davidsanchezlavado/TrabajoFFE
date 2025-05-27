package Controlador;

import BaseDeDatos.PlataformaDAO;
import Modelo.Plataforma;
import java.util.List;

public class PlataformaControlador {
    private PlataformaDAO plataformaDAO;

    public PlataformaControlador() {
        this.plataformaDAO = new PlataformaDAO();
    }

    public boolean agregarPlataforma(String nombre, String paisOrigen) {
        //Ponemos Restricciones al agregar una plataforma
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre de la plataforma no puede estar vacío.");
            return false;
        }
        Plataforma nuevaPlataforma = new Plataforma(nombre.trim(), paisOrigen != null ? paisOrigen.trim() : "");
        return plataformaDAO.insertarPlataforma(nuevaPlataforma);
    }

    public boolean actualizarPlataforma(int id, String nombre, String paisOrigen) {
        //Ponemos Restricciones al agregar una plataforma
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre de la plataforma no puede estar vacío.");
            return false;
        }
        Plataforma plataformaActualizada = new Plataforma(id, nombre.trim(), paisOrigen != null ? paisOrigen.trim() : "");
        return plataformaDAO.actualizarPlataforma(plataformaActualizada);
    }

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