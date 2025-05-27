package Modelo;

public class Plataforma {
    private int id;
    private String nombre;
    private String paisOrigen;

    // Constructor vacío (necesario para algunos frameworks)
    public Plataforma() {
    }

    // Constructor con todos los campos
    public Plataforma(int id, String nombre, String paisOrigen) {
        this.id = id;
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }

    // Constructor sin ID
    public Plataforma(String nombre, String paisOrigen) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }

    // Getters y Setters para cada atributo
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