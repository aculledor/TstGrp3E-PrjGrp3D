package SubsistemaIncidencias;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GestorIncidencias implements IGestorIncidencias {

	private ArrayList<Incidencia> incidencias;
	private Integer contadorIncidencias;

	public GestorIncidencias() {
		incidencias = new ArrayList<Incidencia>();
		contadorIncidencias = 0;
	}

	public ArrayList<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	@Override
	public Incidencia crearIncidencia(String ciudadano, String dni, String telefono, Date fechaReporte,
			String descripcion, String localizacion, String tipo) throws Exception {
		// Comprobacion de nulls
		if (ciudadano == null) {
			throw new Exception("el ciudadano introducido es null");
		}
		if (dni == null) {
			throw new Exception("el dni introducido es null");
		}
		if (telefono == null) {
			throw new Exception("el telefono introducido es null");
		}
		if (fechaReporte == null) {
			throw new Exception("la fecha introducida es null");
		}
		if (descripcion == null) {
			throw new Exception("la descripción introducida es null");
		}
		if (localizacion == null) {
			throw new Exception("la localizacion introducida es null");
		}
		if (tipo == null) {
			throw new Exception("el tipo introducido es null");
		}

		// Comprobacion campo ciudadano
		if (ciudadano.length() > 40 || ciudadano.length() < 1) {
			throw new Exception("nombre de ciudadano demasiado largo");
		}
		// Comprobacion campo dni (Función privada abajo)
		if (dniValido(dni) == false) {
			throw new Exception("dni introducido no valido");

		}
		// Comprobacion campo telefono
		if (telefono.length() < 9 || telefono.length() > 40) {
			throw new Exception("longitud de numero de teléfono no válida");
		} else {
			for (int i = 0; i < telefono.length(); i++) {
				if (Character.isDigit(telefono.charAt(i)) == false) {
					throw new Exception("formato del numero de telefono no válido");
				}
			}
		}
		// Comprobacion campo descripcion
		if (descripcion.length() > 400) {
			throw new Exception("descripcion demasiado larga");

		}
		// Comprobacion campo localizacion
		if (localizacion.length() > 100) {
			throw new Exception("localizacion demasiado larga");
		}
		// Comprobacion campo tipo
		if (!(tipo.equals("Suministro") || tipo.equals("Limpieza") || tipo.equals("Urbanística")
				|| tipo.equals("Legislativa") || tipo.equals("Otros"))) {
			throw new Exception("tipo no válido");
		}

		// Creamos el nuevo id de la incidencia
		int id = ++this.contadorIncidencias;

		// Creamos la incidencia
		Incidencia inc = new Incidencia(id, ciudadano, descripcion, dni, localizacion, telefono, tipo, fechaReporte);

		// La añadimos a la lista de incidencias existente
		this.incidencias.add(inc);

		return inc;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<Incidencia> filtrarIncidencias(HashMap<String, Object> filtros) {
		// Si se reciben
		Date fecha = null;
		String tipo = null;
		String localizacion = null;
		String prueba = "Hola";
		Date prueba2 = new Date();

		// Se comprueba que el HashMap no sea nulo.
		if (filtros == null) {
			return null;
		} else if (filtros.isEmpty()) {
			return this.incidencias;
		} else {
			ArrayList<String> claves = new ArrayList<String>();

			claves.add("fecha");
			claves.add("localizacion");
			claves.add("tipo");

			// Ahora, se obtienen los valores que se aportan en el HashMap.
			for (Map.Entry<String, Object> entry : filtros.entrySet()) {
				// Para cada entrada, comprobamos si la clave es válida. Si no es válida, la
				// función devuelve null:
				if (!claves.contains(entry.getKey())) {
					return null;
				}

				// Si es válida, obtenemos el valor, y realizamos las comprobaciones
				// correspondientes.
				switch (entry.getKey()) {
				case "fecha":
					if (entry.getValue() == null) {
						fecha = null;
					} else {
						// Si es del tipo Date.
						if (entry.getValue().getClass().equals(prueba2.getClass())) {
							fecha = (Date) entry.getValue();
						}
						// Si no lo es, se devuelve null
						else {
							return null;
						}
					}
					break;
				case "localizacion":
					if (entry.getValue() == null) {
						localizacion = null;
					} else {
						// Si es del tipo String.
						if (entry.getValue().getClass().equals(prueba.getClass())) {
							localizacion = (String) entry.getValue();
							// Una vez sabemos que es un String, comprobamos que cumple las restricciones.
							// Si las cumple, continua la ejecución. Si no, el filtrado devuelve null.
							if (localizacion.length() > 100) {
								return null;
							}
						}
						// Si no lo es, se devuelve null
						else {
							return null;
						}
					}
					break;
				case "tipo":
					if (entry.getValue() == null) {
						tipo = null;
					} else {
						// Si es del tipo String.
						if (entry.getValue().getClass().equals(prueba.getClass())) {
							tipo = (String) entry.getValue();
							// Una vez sabemos que es un String, comprobamos que cumple las restricciones.
							// Si las cumple, continua la ejecución. Si no, el filtrado devuelve null.
							if (!(tipo.equals("Suministro") || tipo.equals("Limpieza") || tipo.equals("Urbanistica")
									|| tipo.equals("Legislativa") || tipo.equals("Otros"))) {
								return null;
							}
						}
						// Si no lo es, se devuelve null
						else {
							return null;
						}
					}
					break;
				}
			}
		}

		ArrayList<Incidencia> incFiltradas = new ArrayList<Incidencia>();
		ArrayList<Incidencia> aux = new ArrayList<Incidencia>();

		int tam = this.incidencias.size();

		if (fecha != null) {
			// Si la fecha coincide, las añadimos.
			for (int i = 0; i < tam; i++) {
				if (this.incidencias.get(i).getFechaReporte().getDate() == fecha.getDate()
						&& this.incidencias.get(i).getFechaReporte().getMonth() == fecha.getMonth()
						&& this.incidencias.get(i).getFechaReporte().getYear() == fecha.getYear()) {
					incFiltradas.add(this.incidencias.get(i));
				}
			}
		} else {
			incFiltradas.addAll(this.incidencias);
		}

		tam = incFiltradas.size();

		if (localizacion != null) {
			for (int i = 0; i < tam; i++) {
				if (incFiltradas.get(i).getLocalizacion().equals(localizacion)) {
					aux.add(incFiltradas.get(i));
				}
			}
		} else {
			aux.addAll(incFiltradas);
		}

		incFiltradas.clear();
		tam = aux.size();

		if (tipo != null) {
			for (int i = 0; i < tam; i++) {
				if (aux.get(i).getTipo().equals(tipo)) {
					incFiltradas.add(aux.get(i));
				}
			}
		} else {
			incFiltradas.addAll(aux);
		}

		return incFiltradas;
	}

	@Override
	public ArrayList<Incidencia> filtrarSinAsignar() {
		ArrayList<Incidencia> incSinAsig = new ArrayList<Incidencia>();

		// Recorremos la lista de incidencias.
		for (Incidencia inc : this.incidencias) {
			// Las que no estén asignadas, es decir, su proceso sea igual a null, son
			// añadidas
			// a la lista que devuelve la función.
			if (inc.getProceso() == null) {
				incSinAsig.add(inc);
			}
		}

		return incSinAsig;
	}

	@Override
	public void modificarIncidencia(Incidencia incidencia) throws Exception {
		Incidencia incidenciaAModificar;
		// Lo primero que hacemos es comprobar que la incidencia recibida no es null.
		if (incidencia == null) {
			throw new Exception("la incidencia recibida es null");
		}

		// Si algún campo es null, salta una excepcion, ya que ningun campo de la
		// incidencia puede ser null
		if (incidencia.getId() == null) {
			throw new Exception("el id introducido es null");
		}
		if (incidencia.getCiudadano() == null) {
			throw new Exception("el ciudadano introducido es null");
		}
		if (incidencia.getDni() == null) {
			throw new Exception("el dni introducido es null");
		}
		if (incidencia.getTelefono() == null) {
			throw new Exception("el telefono introducido es null");
		}
		if (incidencia.getFechaReporte() == null) {
			throw new Exception("la fecha introducida es null");
		}
		if (incidencia.getDescripcion() == null) {
			throw new Exception("la descripción introducida es null");
		}
		if (incidencia.getLocalizacion() == null) {
			throw new Exception("la localizacion introducida es null");
		}
		if (incidencia.getTipo() == null) {
			throw new Exception("el tipo introducido es null");
		}

		// Ahora, comprobamos que el id que tiene sea de una incidencia existente, y
		// obtenemos dicha incidencia
		if (incidenciaExiste(incidencia.getId()) == false) {
			throw new Exception("no existe una incidencia con dicho id");
		} else {
			incidenciaAModificar = incidenciaPorId(incidencia.getId());
		}

		// Si la incidencia ya tiene proceso asignado, se indica que no se puede
		// modificar.
		if (incidenciaAModificar.getProceso() != null) {
			throw new Exception("la incidencia que se quiere modificar tiene asignada un proceso");
		}

		// Para cada uno de sus campos, comprobamos que sea válido, y actualizamos el de
		// la incidencia correspondiente

		// COMPROBACIONES CIUDADANO
		if (incidencia.getCiudadano().length() > 40 || incidencia.getCiudadano().length() < 1) {
			throw new Exception("nombre de ciudadano demasiado largo");
		} else {
			// Actualizacion ciudadano
			incidenciaAModificar.setCiudadano(incidencia.getCiudadano());
		}

		// COMPROBACIONES DNI
		if (dniValido(incidencia.getDni()) == false) {
			throw new Exception("dni introducido no valido");
		} else {
			// Actualizacion dni
			incidenciaAModificar.setDni(incidencia.getDni());
		}

		// COMPROBACIONES TELEFONO
		if (incidencia.getTelefono().length() < 9 || incidencia.getTelefono().length() > 40) {
			throw new Exception("longitud de numero de teléfono no válida");
		} else {
			for (int i = 0; i < incidencia.getTelefono().length(); i++) {
				if (Character.isDigit(incidencia.getTelefono().charAt(i)) == false) {
					throw new Exception("formato del numero de telefono no válido");
				}
			}
		}
		// Actualizacion telefono
		incidenciaAModificar.setTelefono(incidencia.getTelefono());

		// COMPROBACIONES DESCRIPCION
		if (incidencia.getDescripcion().length() > 400) {
			throw new Exception("descripcion demasiado larga");
		} else {
			// Actualizacion descripcion
			incidenciaAModificar.setDescripcion(incidencia.getDescripcion());
		}

		// COMPROBACIONES LOCALIZACION
		if (incidencia.getLocalizacion().length() > 100) {
			throw new Exception("localizacion demasiado larga");
		} else {
			// Actualizacion localizacion
			incidenciaAModificar.setLocalizacion(incidencia.getLocalizacion());
		}

		// COMPROBACIONES TIPO
		if (!(incidencia.getTipo().equals("Suministro") || incidencia.getTipo().equals("Limpieza")
				|| incidencia.getTipo().equals("Urbanística") || incidencia.getTipo().equals("Legislativa")
				|| incidencia.getTipo().equals("Otros"))) {
			throw new Exception("tipo no válido");
		} else {
			// Actualizacion localizacion
			incidenciaAModificar.setTipo(incidencia.getTipo());
		}
	}

	private boolean dniValido(String dni) {

		String letraDni[] = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V",
				"H", "L", "C", "K", "E" };

		// Comprobamos que la longitud del dni sea 9 y el ultimo caracter sea una letra.
		if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
			return false;
		}

		// Comprobamos que los 8 primeros caracteres son numeros.
		for (int i = 0; i < dni.length() - 1; i++) {
			if (Character.isDigit(dni.charAt(i)) == false) {
				return false;
			}
		}

		// Obtenemos la letra del dni y la pasamos a mayúscula, para unificar las
		// comprobaciones.
		String miletra = dni.substring(8).toUpperCase();
		int miDni = Integer.parseInt(dni.substring(0, 8));
		int resto = miDni % 23;
		String letraCorrecta = letraDni[resto];

		// Comprobamos que la letra introducida sigue el algoritmo.
		if (letraCorrecta.equals(miletra) == false) {
			return false;
		}
		return true;
	}

	private Incidencia incidenciaPorId(Integer id) {
		Incidencia inc = null;
		for (Incidencia inci : this.incidencias) {
			if (inci.getId().equals(id)) {
				inc = inci;
			}
		}
		return inc;
	}

	private boolean incidenciaExiste(Integer id) {
		for (Incidencia inc : this.incidencias) {
			if (inc.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

}
