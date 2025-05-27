// src/Modelo/Plataforma.java
package Modelo;

public class Plataforma {
    private int id;
    private String nombre;
    private String paisOrigen;

    // Constructor vacío (necesario para algunos frameworks o para inicializar con valores por defecto)
    public Plataforma() {
    }

    // Constructor con todos los campos (útil para crear objetos completos)
    public Plataforma(int id, String nombre, String paisOrigen) {
        this.id = id;
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }

    // Constructor sin ID (útil para nuevas plataformas que aún no tienen ID de la BD)
    public Plataforma(String nombre, String paisOrigen) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }

    // Getters y Setters para cada atributo (encapsulamiento)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    @Override
    public String toString() {
        return nombre; // Útil para mostrar en ComboBoxes o listas
    }
}