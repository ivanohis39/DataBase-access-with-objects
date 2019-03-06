
public class Jugador {

	private String nombre;
	private String apellidos;
	private String nacionalidad;
	private int dorsal;
	
	Equipo equipo;

	public Jugador(String nombre, String apellidos, String nacionalidad, int dorsal, Equipo equipo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
		this.equipo = equipo;
	}

	public Jugador(String nombre, String apellidos, String nacionalidad, int dorsal) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
	}

	public Jugador() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	@Override
	public String toString() {
	String rdo = "Jugador [nombre=" + nombre + ", apellidos=" + apellidos + ", nacionalidad=" + nacionalidad + ", dorsal="
				+ dorsal + ", \n\tequipo=" + equipo + "]";
	if(this.getEquipo()!= null) {
		rdo = rdo + " equipo " + getEquipo().getNombre() + "]";
	}
	return rdo;
	}
	
}
