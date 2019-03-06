import java.util.ArrayList;

public class Equipo {

	private String nombre;
	private String categoria;
	private String pais;
	private String estadio;
	
	ArrayList<Jugador> jugadores = new ArrayList<>();

	public Equipo(String nombre, String categoria, String pais, String estadio) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.pais = pais;
		this.estadio = estadio;
	}

	public Equipo() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void addJugadores(Jugador jug) {
		this.jugadores.add(jug);
	}

	@Override
	public String toString() {
		return "Equipo [nombre=" + nombre + ", categoria=" + categoria + ", pais=" + pais + ", estadio=" + estadio
				+ "]";
	}
	
}
