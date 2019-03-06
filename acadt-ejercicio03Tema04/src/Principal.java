import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.constraints.UniqueFieldValueConstraint;

public class Principal {

	//creamos la conexion con la BD
	public static ObjectContainer GetConexioBD() {

		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		// cambiamos el nivel de actualizacion
		config.common().activationDepth(5);
		config.common().updateDepth(5);

		// le damos un indice al campo del nombre del equipo
		config.common().objectClass(Equipo.class).objectField("nombre").indexed(true);
		// creamos/abrimos la conexion con la configuracion creada
		ObjectContainer db = Db4oEmbedded.openFile(config, "DBEquipos");

		return db;

	}

	//insertamos el equipo en la BD sin jugador
	public static void insertarEquipo(Equipo equipo) {
		ObjectContainer db = GetConexioBD();
		db.store(equipo);
		db.commit();
		db.close();
	}

	//insertamos el jugador en la BD sin equipo
	public static void insertarJugador(Jugador jugador) {
		ObjectContainer db = GetConexioBD();
		db.store(jugador);
		db.commit();
		db.close();
	}

	public static void setEquipoToJugador(Jugador jugador, Equipo equipo) {
		ObjectContainer db = GetConexioBD();

		Jugador jug_query = pideBusquedaJugador();
		Equipo team_query = pideBusquedaEquipo();

		// buscamos lo jugadores y los equipos
		ObjectSet<Jugador> objectJugadores = db.queryByExample(jug_query);
		ObjectSet<Equipo> objectEquipo = db.queryByExample(team_query);

		if (objectJugadores.size() > 0) {
			// existe el jugador y lo hemos encontrado
			Jugador jug = objectJugadores.next();
			if (objectEquipo.size() > 0) {
				jug.setEquipo(objectEquipo.next());// le damos al jugador el equipo
				db.store(jug);
			} else {
				System.out.println("El equipo no existe en esta BD.");
			}
		} else {
			System.out.println("El jugador no existe en esta BD.");
		}
		db.commit();
		db.close();
		AddJugadorToEquipo(jug_query, team_query);
	}

	public static void AddJugadorToEquipo(Jugador jugador, Equipo equipo) {
		ObjectContainer db = GetConexioBD();

		// buscamos lo jugadores y los equipos
		ObjectSet<Jugador> objectJugadores = db.queryByExample(jugador);
		ObjectSet<Equipo> objectEquipo = db.queryByExample(equipo);

		if (objectEquipo.size() > 0) {
			// existe y lo hemos encontrado
			Equipo team = objectEquipo.next();
			if (objectJugadores.size() > 0) {
				//aniadimos el jugador al equipo
				team.addJugadores(objectJugadores.next());
				db.store(team);
			}
		}
		db.commit();
		db.close();
	}

	public static void modificarJugador() {
		ObjectContainer db = GetConexioBD();
		// pedimos los datos del jugador a modificar
		Jugador jug_query = pideBusquedaJugador();

		ObjectSet<Jugador> objectJugador = db.queryByExample(jug_query);
		if (objectJugador.size() > 0) {
			Jugador jugador = objectJugador.next();
			System.out.println("\tIntroduce los nuevos datos:");
			// seteamos los nuevos datos
			jugador.setNombre(pideString("Nuevo nombre:"));
			jugador.setApellidos(pideString("Nuevo apellido:"));
			jugador.setNacionalidad(pideString("nacionalidad:"));
			jugador.setDorsal(pideInteger("Dorsal:"));

			db.store(jugador);
			System.out.println("Jugador modificado");
		} else {
			System.out.println("El jugador solicitado no se encuentra en la base de datos.");
		}
		db.commit();
		db.close();
	}

	public static void modificarEquipo() {
		ObjectContainer db = GetConexioBD();
		// pedimos el nombre del equipo que queremos modificar
		Equipo equipo_query = pideBusquedaEquipo();
		ObjectSet<Equipo> objectEquipo = db.queryByExample(equipo_query);
		if (objectEquipo.size() > 0) {
			Equipo team = objectEquipo.next();// nos creamos un objeto con ese equipo
			System.out.println("\tIntroduce los nuevos datos:");
			// le introducimos todos los nuevos datos
			team.setNombre(pideString("Nuevo nombre del equipo:"));
			team.setCategoria(pideString("Categoria del equipo:"));
			team.setPais(pideString("Pais del equipo:"));
			team.setEstadio(pideString("Estadio:"));

			db.store(team);
			System.out.println("Equipo modificado");
		} else {
			System.out.println("El equipo no existe.");
		}
		db.commit();
		db.close();
	}

	public static void eliminarJugador() {
		ObjectContainer db = GetConexioBD();
		// pedimos el juigador que queremos eliminar
		Jugador juga_query = pideBusquedaJugador();

		ObjectSet<Jugador> objectJugador = db.queryByExample(juga_query);
		if (objectJugador.size() > 0) {
			Jugador jugador = objectJugador.next();
			db.delete(jugador);// eliminamos el jugador seleccionado
			System.out.println("Jugador eliminado.");
		} else {
			System.out.println("No se ha encotrado al jugador especificado.");
		}
		db.commit();
		db.close();
	}

	public static void eliminarEquipo() {
		ObjectContainer db = GetConexioBD();
		// pedimos el equipo que queremos eliminar
		Equipo team_query = pideBusquedaEquipo();

		ObjectSet<Equipo> objectEquipo = db.queryByExample(team_query);

		if (objectEquipo.size() > 0) {
			Equipo equipo = objectEquipo.next();
			db.delete(equipo);// borramos el equipo de la bd
			System.out.println("Equipo eliminado");
		} else {
			System.out.println("No se ha encotrado al equipo especificado.");
		}
		db.commit();
		db.close();
	}

	public static void mostrarEquipos() {
		ObjectContainer db = GetConexioBD();
		// Que nos ensenie todo lo que hara en esa clase
		ObjectSet<Equipo> objectEquipo = db.queryByExample(Equipo.class);
		ObjectSet<Jugador> objectJugadores = db.queryByExample(Jugador.class);
		while (objectEquipo.hasNext()) {
			System.out.println(objectEquipo.next());
		}
		System.out.println();
		while(objectJugadores.hasNext()){
			System.out.println(objectJugadores.next());
		}
		db.close();
	}

	public static void mostrarJugadores() {
		ObjectContainer db = GetConexioBD();
		String equipo = pideString("Nombre del equipo:");
		// pedimos el nombre del equipo y buscamos al jugado por su equipo
		Equipo team = new Equipo(equipo, null, null, null);
		Jugador jug_query = new Jugador(null, null, null, 0, team);// solo nos interesa el nombre del equipo
		ObjectSet<Jugador> objectSetJugador = db.queryByExample(jug_query);

		if (objectSetJugador.size() > 0) {
			// mostramos todo lo que haya dentro del equipo
			while (objectSetJugador.hasNext()) {
				System.out.println(objectSetJugador.next());
			}
		}
		db.close();
	}

	public static void cambiarJugadorDeEquipo() {
		ObjectContainer db = GetConexioBD();

		Equipo team_query = pideBusquedaEquipo();// pedimos el equipo al que se lo queremos aniadir
		Jugador player_query = pideBusquedaJugador();// pedimos el jugador

		ObjectSet<Jugador> objectJugador = db.queryByExample(player_query);
		ObjectSet<Equipo> objectEquipo = db.queryByExample(team_query);

		if (objectJugador.size() > 0) {
			Jugador jug = objectJugador.next();
			if (objectEquipo.size() > 0) {
				Equipo equi = objectEquipo.next();
				if (jug.getEquipo() != null) {// si el jugador no tiene equipo no hacemos nada
					// cogemos el equipo del jugador
					Equipo teamID = jug.getEquipo();
					// al jugador le estblecemos el equipo deseado
					jug.setEquipo(equi);
					equi.getJugadores().add(jug);// al equipo le aniadimos el jugador
					teamID.getJugadores().remove(jug);// eliminamos el jugador del equipo anterior

					db.store(equi);
					db.store(jug);
					db.store(teamID);
				} else {
					System.out.println("Ese jugador no tiene ningun equipo.");
				}
			} else {
				System.out.println("Equipo no encotrado.");
			}
		} else {
			System.out.println("Jugador no encotrado.");
		}
		db.commit();
		db.close();
	}

	//pedimos solo dos datos para faciliar su busqueda
	public static Equipo pideBusquedaEquipo() {
		Equipo equipo = new Equipo();
		equipo.setNombre(pideString("Nombre del equipo:"));
		equipo.setPais(pideString("Pais del equipo:"));
		return equipo;
	}

	public static Jugador pideBusquedaJugador() {
		Jugador jugador = new Jugador();
		jugador.setNombre(pideString("Nombre del jugador:"));
		jugador.setApellidos(pideString("Apellidos:"));
		return jugador;
	}

	//los datos a aniadir en el equipo
	public static Equipo pideEquipo() {
		Equipo equipo = new Equipo(pideString("Nombre del equipo:"), pideString("Categoria:"), pideString("Pais:"),
				pideString("Estadio:"));
		return equipo;
	}

	//datos a aniadir en el jugador
	public static Jugador pideJugador() {
		Jugador jugador = new Jugador(pideString("Nombre del jugador:"), pideString("Apellidos:"),
				pideString("Nacionalidad:"), pideInteger("Dorsal:"));
		return jugador;
	}

	public static int pideInteger(String cadena) {
		Scanner entrada = new Scanner(System.in);
		System.out.println(cadena);
		return entrada.nextInt();
	}

	public static String pideString(String cadena) {
		Scanner entrada = new Scanner(System.in);
		System.out.println(cadena);
		return entrada.nextLine();
	}

	public static void menu() {
		System.out.println("\t\t***---*** MENU ***---***");
		System.out.println("Pulsa 1 para introducir un equipo.");
		System.out.println("Pulsa 2 para introducir un jugador.");
		System.out.println("Pulsa 3 para establecer el equipo de un jugador.");
		System.out.println("Pulsa 4 para aniadir un jugador a un equipo.");
		System.out.println("Pulsa 5 para modificar un jugador.");
		System.out.println("Pulsa 6 para modificar un equipo.");
		System.out.println("Pulsa 7 para eliminar un jugador.");
		System.out.println("Pulsa 8 para eliminar un equipo.");
		System.out.println("Pulsa 9 para mostrar todos lo equipos.");
		System.out.println("Pulsa 10 para mostrar todos los jugadores de un equipo.");
		System.out.println("Pulsa 11 para cambiar a un jugador de equipo.");
		System.out.println("Pulsa 12 para salir del programa.");
		System.out.println();
	}

	public static void mostrarMenu() {
		int opcion;

		//estos dos objetos si queremos los podemos omitir pero deneria modificar parte del 
		//codigo y me da mucha pereza
		Equipo equipo = new Equipo(); 
		Jugador jugador = new Jugador();
		do {
			menu();
			opcion = pideInteger("Introduce la opcion que desees:");
			System.out.println();
			switch (opcion) {
			case 1: {
				System.out.println("\t\t***---*** Introduce los datos del Equipo ***---***");
				equipo = pideEquipo();
				insertarEquipo(equipo);
				break;
			}
			case 2: {
				System.out.println("\t\t***---*** Introduce los datos del Jugador ***---***");
				jugador = pideJugador();
				insertarJugador(jugador);
				break;
			}
			case 3: {
				System.out.println(
						"\t\t***---*** Introduce los datos necesarios para establecer el equipo del jugador que desees ***---***");
				setEquipoToJugador(jugador, equipo);
				break;
			}
			case 4: {
				System.out.println(
						"\t\t***---*** Introduce los datos necesario para aniadir un jugador al equipo deseeado ***---***");
				// AddJugadorToEquipo(jugador, equipo);
				System.out.println("ESTE METODO SE HACE DEL TIRON CON EL ANTERIOR.");
				break;
			}
			case 5: {
				System.out.println("\t\t***---*** Modifica el Jugador ***---***");
				modificarJugador();
				break;
			}
			case 6: {
				System.out.println("\t\t***---*** Modifica el Equipo ***---***");
				modificarEquipo();
				break;
			}
			case 7: {
				System.out.println("\t\t***---*** Eliminar un jugador ***---***");
				eliminarJugador();
				break;
			}
			case 8: {
				System.out.println("\t\t***---*** Eliminar un Equipo ***---***");
				eliminarEquipo();
				break;
			}
			case 9: {
				System.out.println("\t\t***---*** Equipos ***---***");
				mostrarEquipos();
				break;
			}
			case 10: {
				System.out.println("\t\t***---*** Jugadores ***---***");
				mostrarJugadores();
				break;
			}
			case 11: {
				System.out.println("\t\t***---*** Cambiar jugadores de equipo ***---***");
				cambiarJugadorDeEquipo();
				break;
			}
			case 12: {
				System.out.println("Te has salido del sistema. Hasta pronto.");
				break;
			}
			default: {
				System.out.println("Opcion incorrecta. Intentalo de nuevo.");
				break;
			}
			}
		} while (opcion != 12);
	}

	public static void main(String[] args) {

		mostrarMenu();
	}
}
