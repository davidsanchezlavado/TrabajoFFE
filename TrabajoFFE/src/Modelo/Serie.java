package Modelo;

public class Serie {
    private int id;
    private String titulo;
    private String genero;
    private int numTemporadas;
    private int anoLanzamiento;
    private int idPlataforma; // Clave foránea para la plataforma
    private Plataforma plataforma; // Objeto Plataforma para tener la información completa

    public Serie() {
    }

    public Serie(int id, String titulo, String genero, int numTemporadas, int anoLanzamiento, int idPlataforma) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.numTemporadas = numTemporadas;
        this.anoLanzamiento = anoLanzamiento;
        this.idPlataforma = idPlataforma;
    }

    // Constructor para nuevas series (sin ID, ya que lo generará automáticamente)
    public Serie(String titulo, String genero, int numTemporadas, int anoLanzamiento, int idPlataforma) {
        this.titulo = titulo;
        this.genero = genero;
        this.numTemporadas = numTemporadas;
        this.anoLanzamiento = anoLanzamiento;
        this.idPlataforma = idPlataforma;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumTemporadas() {
        return numTemporadas;
    }

    public void setNumTemporadas(int numTemporadas) {
        this.numTemporadas = numTemporadas;
    }

    public int getAnoLanzamiento() {
        return anoLanzamiento;
    }

    public void setAnoLanzamiento(int anoLanzamiento) {
        this.anoLanzamiento = anoLanzamiento;
    }

    public int getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(int idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public String toString() {
        return titulo + " (" + (plataforma != null ? plataforma.getNombre() : "N/A") + ")";
    }
}