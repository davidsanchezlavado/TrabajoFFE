package Modelo;

public class Serie {
	
	private int id;
    private String titulo;
    private String genero;
    private int numeroTemporadas;
    private int anioLanzamiento;
    private Plataforma plataforma;

    public Serie(int id, String titulo, String genero, int numeroTemporadas, int anioLanzamiento, Plataforma plataforma) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.numeroTemporadas = numeroTemporadas;
        this.anioLanzamiento = anioLanzamiento;
        this.plataforma = plataforma;
    }

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

    public int getNumeroTemporadas() { 
    	return numeroTemporadas; 
    }
    
    public void setNumeroTemporadas(int numeroTemporadas) { 
    	this.numeroTemporadas = numeroTemporadas; 
    }

    public int getAnioLanzamiento() { 
    	return anioLanzamiento; 
    }
    
    public void setAnioLanzamiento(int anioLanzamiento) { 
    	this.anioLanzamiento = anioLanzamiento; 
    }

    public Plataforma getPlataforma() { 
    	return plataforma; 
    }
    
    public void setPlataforma(Plataforma plataforma) { 
    	this.plataforma = plataforma; 
    }
	
}
