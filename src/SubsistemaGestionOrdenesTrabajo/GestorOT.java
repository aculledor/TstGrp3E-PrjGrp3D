package SubsistemaGestionOrdenesTrabajo;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;

public class GestorOT implements IGestorOT {

	private ArrayList<OrdenTrabajo> ordenesTrabajo;
	private GestorProcesos gestorProc;
	private Integer contadorOT;

	public GestorOT() {
		this.ordenesTrabajo = new ArrayList<OrdenTrabajo>();
		this.contadorOT = 0;
	}

	public GestorOT(GestorProcesos gestor) {
		this.ordenesTrabajo = new ArrayList<OrdenTrabajo>();
		this.gestorProc = gestor;
		this.contadorOT = 0;
	}

	public ArrayList<OrdenTrabajo> getOrdenesTrabajo() {
		return this.ordenesTrabajo;
	}

	public GestorProcesos getGestorProc() {
		return gestorProc;
	}

	public void setGestorProc(GestorProcesos gestorProc) {
		this.gestorProc = gestorProc;
	}

	@Override
	public OrdenTrabajo crearOrdenTrabajo(String empresaResponsable, String estado, Integer duracionEstimada,
			Date fechaInicio, String descripcion, Float coste, String material, ArrayList<String> personal,
			ArrayList<Float> presupuestos, Integer proceso) throws Exception {

		Proceso proc = null;

		if (empresaResponsable != null && empresaResponsable.length() > 30) {
			throw new Exception("empresaResponsable no valida");
		}

		if (estado != null && !(estado.equals("sin comenzar") || estado.equals("iniciado") || estado.equals("avanzado")
				|| estado.equals("finalizado"))) {
			throw new Exception("estado no valido");
		}

		if (duracionEstimada != null && duracionEstimada.intValue() <= 0) {
			throw new Exception("duracion estimada no valida");
		}

		if (descripcion == null || descripcion.length() > 400) {
			throw new Exception("descripcion no valida");
		}

		if (coste != null && coste <= 0) {
			throw new Exception("coste no valido");
		}

		if (material != null && material.length() > 150) {
			throw new Exception("material no valido");
		}

		// comprobacion de que el personal es un array de menos de 10 elementos de dnis
		// validos, en caso de que no sea nulo
		if (personal != null) {
			if (personal.size() > 10) {
				throw new Exception("personal no valido");
			} else {
				String dni;
				for (int i = 0; i < personal.size(); i++) {
					dni = personal.get(i);
					if (this.dniValido(dni) == false) {
						throw new Exception("dni del personal no valido");
					}
				}
			}
		}

		// comprobacion de que los presupuestos son por lo menos 3 y son reales
		// positivos
		if (presupuestos != null) {
			if (presupuestos.size() < 3) {
				throw new Exception("numero de presupuestos no valido");
			} else {
				Float valor;
				for (int i = 0; i < presupuestos.size(); i++) {
					valor = presupuestos.get(i);
					if (valor <= 0) {
						throw new Exception("presupuesto no valido");
					}
				}
			}
		}

		// comprobacion de que el proceso existe en el sistema
		for (Proceso p : this.gestorProc.getProcesos()) {
			if (p.getId().equals(proceso)) {
				proc = p;
				break;
			}
		}
		if (proc == null) {
			throw new Exception("proceso no existente en el sistema");
		}

		// incrementamos el contador de ids de OT
		this.contadorOT++;

		// llamada al constructor para crear la orden de trabajo
		OrdenTrabajo orden = new OrdenTrabajo(this.contadorOT, coste, descripcion, duracionEstimada, empresaResponsable,
				estado, fechaInicio, material, personal, presupuestos);

		this.ordenesTrabajo.add(orden);

		ArrayList<Integer> ordenes = new ArrayList<Integer>();
		// vinculamos el proceso con la orden de trabajo
		ordenes.add(orden.getId());
		this.gestorProc.vincularOT(proc.getId(), ordenes);

		return orden;
	}

	@Override
	public Float acumuladoEmpresa(String empresa) throws Exception {
		Float acumulado = Float.valueOf(0);
		// comprobamos que la empresa no sea null
		if (empresa != null && empresa.length() < 30) {
			// obtenemos la suma de los costes de las OT dirigidas por tal empresa
			for (OrdenTrabajo orden : ordenesTrabajo) {
				if (orden.getEmpresaResponsable().equals(empresa)) {
					acumulado += orden.getCoste();
				}
			}
			if (acumulado == 0)
				throw new Exception("no hay ordenes de trabajo con esa empresa");
		} else {
			throw new Exception("empresa responsable no valida");
		}
		return acumulado;
	}

	@Override
	public Float acumuladoProceso(Integer proceso) throws Exception {
		Float acumulado = Float.valueOf(0);
		// comprobamos que el proceso no sea null
		if (proceso != null) {
			// obtenemos la suma de los costes de las OT vinculadas a tal proceso
			for (OrdenTrabajo orden : ordenesTrabajo) {
				if (orden.getProceso().getId().equals(proceso)) {
					acumulado += orden.getCoste();
				}
			}
			if (acumulado == 0)
				throw new Exception("no hay procesos con tal id en el sistema");
		} else {
			throw new Exception("el proceso es null");
		}
		return acumulado;
	}

	@Override
	public ArrayList<OrdenTrabajo> filtrarOT(HashMap<String, Object> filtros) {
		ArrayList<OrdenTrabajo> ordFiltradas = new ArrayList<OrdenTrabajo>();
		ArrayList<OrdenTrabajo> aux = new ArrayList<OrdenTrabajo>();
		// se puede filtrar por empresa, fechaInicio, duracion y estado
		Date fechaInicio = null;
		String estado = null;
		String empresaResponsable = null;
		Integer duracion = null;

		// Se comprueba que el HashMap no sea nulo:
		if (filtros == null) {
			return null;
		} else if (filtros.isEmpty()) {
			return this.getOrdenesTrabajo();
		} else {
			// Ahora, se obtienen los valores que se aportan en el HashMap.
			for (Map.Entry<String, Object> entry : filtros.entrySet()) {
				if (entry.getKey().equals("fechaInicio")) {
					if (entry.getValue() == null) {
						fechaInicio = null;
					} else {
						// Si es del tipo Date.
						if (entry.getValue().getClass().equals(new Date().getClass())) {
							fechaInicio = (Date) entry.getValue();
						} else {
							return null;
						}
					}
				} else if (entry.getKey().equals("estado")) {
					if (entry.getValue() == null) {
						estado = null;
					} else {
						// si es de tipo String
						if (entry.getValue().getClass().equals(("").getClass())) {
							estado = (String) entry.getValue();
						} else {
							return null;
						}
					}
				} else if (entry.getKey().equals("empresa")) {
					if (entry.getValue() == null) {
						empresaResponsable = null;
					} else {
						// Si es de tipo String
						if (entry.getValue().getClass().equals(("").getClass())) {
							empresaResponsable = (String) entry.getValue();
						} else {
							return null;
						}
					}
				} else if (entry.getKey().equals("duracion")) {
					if (entry.getValue() == null) {
						duracion = null;
					} else {
						// Si es de tipo Integer
						if (entry.getValue().getClass().equals(Integer.valueOf(0).getClass())) {
							duracion = (Integer) entry.getValue();
						} else {
							return null;
						}
					}
				} else {
					return null;
				}
			}

			// Si la fechaInicio no es null, se realiza el primer filtro.
			if (fechaInicio != null) {
				for (OrdenTrabajo ord : this.ordenesTrabajo) {
					if (ord.getFechaInicio().getDate() == fechaInicio.getDate()
							&& ord.getFechaInicio().getMonth() == fechaInicio.getMonth()
							&& ord.getFechaInicio().getYear() == fechaInicio.getYear()) {
						ordFiltradas.add(ord);
					}
				}
			} else {
				// Si es null, se devuelve la lista sin filtrar.
				ordFiltradas.addAll(this.ordenesTrabajo);
			}

			// Si el estado no es null y es alguno de los validos, se realiza el segundo
			// filtro.
			if (estado != null) {
				if (estado.equals("sin comenzar") || estado.equals("iniciado") || estado.equals("avanzado")
						|| estado.equals("finalizado")) {
					for (OrdenTrabajo ord : ordFiltradas) {
						if (ord.getEstado().equals(estado)) {
							aux.add(ord);
						}
					}
					ordFiltradas.clear();
					ordFiltradas.addAll(aux);
					aux.clear();
				} else {
					return null;
				}
			}

			// Si la duracion no es null y es mayor que 0, se realiza el tercer filtro.
			if (duracion != null) {
				if (duracion.intValue() > 0) {
					for (OrdenTrabajo ord : ordFiltradas) {
						if (ord.getDuracionEstimada().equals(duracion)) {
							aux.add(ord);
						}
					}
					ordFiltradas.clear();
					ordFiltradas.addAll(aux);
					aux.clear();
				} else {
					return null;
				}
			}

			// Si la empresaResponsanle no es null y es de menos de 30 caracteres, se
			// realiza el cuarto filtro.
			if (empresaResponsable != null) {
				if (empresaResponsable.length() <= 30) {
					for (OrdenTrabajo ord : ordFiltradas) {
						if (ord.getEmpresaResponsable().equals(empresaResponsable)) {
							aux.add(ord);
						}
					}
					ordFiltradas.clear();
					ordFiltradas.addAll(aux);
					aux.clear();
				} else {
					return null;
				}
			}

			// Así, si todos eran null, se devuelve la lista completa de ordenes de trabajo
			return ordFiltradas;
		}
	}

	@Override
	public void modificarOT(OrdenTrabajo ordenTrabajo) throws Exception {
		OrdenTrabajo OTAModificar;
		// Lo primero que hacemos es comprobar que la OT recibida no es null.
		if (ordenTrabajo == null) {
			throw new Exception("la OT recibida es null");
		}

		// Ahora, comprobamos que el id que tiene sea de una OT existente, y obtenemos
		// dicha OT
		if (ordenExiste(ordenTrabajo.getId()) == false) {
			throw new Exception("no existe una OT con dicho id");
		} else {
			OTAModificar = OTPorId(ordenTrabajo.getId());
		}

		// Para cada uno de sus campos, comprobamos que sea válido, y actualizamos el de
		// la OT correspondiente
		// Si algun campo no null coincide con el valor actual, se realiza el setter de
		// todas formas, ya que no cambiara el valor.

		// COMPROBACIONES COSTE
		if (ordenTrabajo.getCoste() < 0) {
			throw new Exception("coste de la orden de trabajo incorrecto");
		} else {
			// Actualizacion coste
			OTAModificar.setCoste(ordenTrabajo.getCoste());
		}

		// COMPROBACIONES DESCRIPCION
		if ((ordenTrabajo.getDescripcion() != null && ordenTrabajo.getDescripcion().length() > 400)
				|| ordenTrabajo.getDescripcion() == null) {
			throw new Exception("descripcion introducida incorrecta");
		} else {
			// Actualizacion descripcion
			OTAModificar.setDescripcion(ordenTrabajo.getDescripcion());
		}

		// COMPROBACIONES DURACION ESTIMADA
		if (ordenTrabajo.getDuracionEstimada() != null && ordenTrabajo.getDuracionEstimada() < 0) {
			throw new Exception("duracion estimada no válida");
		} else {
			OTAModificar.setDuracionEstimada(ordenTrabajo.getDuracionEstimada());
		}

		// COMPROBACIONES EMPRESA RESPONSABLE
		if (ordenTrabajo.getEmpresaResponsable() != null) {
			if (ordenTrabajo.getEmpresaResponsable().length() > 30) {
				throw new Exception("empresa responsable no valida");
			} else {
				OTAModificar.setEmpresaResponsable(ordenTrabajo.getEmpresaResponsable());
			}
		}

		// COMPROBACIONES ESTADO
		if ((ordenTrabajo.getEstado() != null && !(ordenTrabajo.getEstado().equals("sin comenzar")
				|| ordenTrabajo.getEstado().equals("iniciado") || ordenTrabajo.getEstado().equals("avanzado")
				|| ordenTrabajo.getEstado().equals("finalizado"))) || ordenTrabajo.getEstado() == null) {
			throw new Exception("estado no valido");
		} else {
			OTAModificar.setEstado(ordenTrabajo.getEstado());
		}

		// COMPROBACIONES FECHA INICIO
		if (ordenTrabajo.getFechaInicio() == null) {
			throw new Exception("fecha de inicio no valida");
		} else {
			OTAModificar.setFechaInicio(ordenTrabajo.getFechaInicio());
		}

		// COMPROBACIONES MATERIAL
		if (ordenTrabajo.getMaterial() != null && ordenTrabajo.getMaterial().length() > 150) {
			throw new Exception("material no valido");
		} else {
			OTAModificar.setMaterial(ordenTrabajo.getMaterial());
		}

		// COMPROBACIONES PERSONAL
		if (ordenTrabajo.getPersonal() != null) {
			if (ordenTrabajo.getPersonal().size() > 10) {
				throw new Exception("personal no valido");
			} else {
				String dni;
				for (int i = 0; i < ordenTrabajo.getPersonal().size(); i++) {
					dni = ordenTrabajo.getPersonal().get(i);
					if (this.dniValido(dni) == false) {
						throw new Exception("el dni " + dni + " no es valido");
					}
				}
				OTAModificar.setPersonal(ordenTrabajo.getPersonal());
			}
		}

		// COMPROBACIONES PRESUPUESTOS
		if (ordenTrabajo.getPresupuestos() != null) {
			if (ordenTrabajo.getPresupuestos().size() < 3) {
				throw new Exception("numero de presupuestos no valido");
			} else {
				Float valor;
				for (int i = 0; i < ordenTrabajo.getPresupuestos().size(); i++) {
					valor = ordenTrabajo.getPresupuestos().get(i);
					if (valor <= 0) {
						throw new Exception("el presupuesto " + valor + " no es valido");
					}
				}
				OTAModificar.setPresupuestos(ordenTrabajo.getPresupuestos());
			}
		}
	}

	// metodo para comprobar si el dni que recibe conmo argumento es un dni valido
	// (8 cifras+letra coordidiendo el codigo de redundancia)
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

	private OrdenTrabajo OTPorId(Integer id) {
		OrdenTrabajo orden = null;
		for (OrdenTrabajo ord : this.ordenesTrabajo) {
			if (ord.getId().equals(id)) {
				orden = ord;
				break;
			}
		}
		return orden;
	}

	private boolean ordenExiste(Integer id) {
		for (OrdenTrabajo ord : this.ordenesTrabajo) {
			if (ord.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean procesoExiste(Integer id) {
		for (Proceso p : this.gestorProc.getProcesos()) {
			if (p.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}
