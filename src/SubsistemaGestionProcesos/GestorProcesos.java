package SubsistemaGestionProcesos;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaIncidencias.Incidencia;

public class GestorProcesos implements IGestorProcesos {

	private ArrayList<Proceso> procesos;
	private GestorIncidencias gestorIncidencias;
	private GestorOT gestorOrdenesTrabajo;
	private Integer idProcesos;// controlará la generación de ids automática

	public GestorProcesos(GestorIncidencias gestorIncidencias, GestorOT gestorOrdenesTrabajo) {
		procesos = new ArrayList<Proceso>();
		this.gestorIncidencias = gestorIncidencias;
		this.gestorOrdenesTrabajo = gestorOrdenesTrabajo;
		idProcesos = 0;
	}

	public ArrayList<Proceso> getProcesos() {
		return this.procesos;
	}

	@Override
	public Proceso crearProceso(String nombre, String estado, String responsable, String servicio, String descripcion,
			Float coste, ArrayList<Integer> incidencias, ArrayList<Integer> ordenesTrabajo) throws Exception {

		int flag = 0;
		Incidencia incAux = null;
		OrdenTrabajo otAux = null;
		ArrayList<Incidencia> incidenciasAux = new ArrayList<Incidencia>();
		ArrayList<OrdenTrabajo> ordenesAux = new ArrayList<OrdenTrabajo>();

		if (nombre == null || estado == null || responsable == null || servicio == null || descripcion == null
				|| coste == null || incidencias == null) {
			throw new Exception("ningún campo excepto las órdenes de trabajo puede ser nulo");
		}

		if (nombre.length() > 20) {
			throw new Exception("nombre demasiado largo");
		}
		if (!(estado.equals("sin comenzar") || estado.equals("planificado") || estado.equals("en construcción")
				|| estado.equals("finalizado"))) {

			throw new Exception("estado incorrecto");
		}
		if (responsable.length() > 40) {
			throw new Exception("responsable demasiado largo");
		}
		if (!(servicio.equals("Suministro") || servicio.equals("Limpieza") || servicio.equals("Urbanística")
				|| servicio.equals("Legalidad") || servicio.equals("Otros"))) {

			throw new Exception("servicio incorrecto");
		}
		if (descripcion.length() > 400) {
			throw new Exception("descripción demasiado larga");
		}
		if (coste <= 0) {
			throw new Exception("el coste debe ser un número positivo");
		}
		if (incidencias.isEmpty()) {
			throw new Exception("se debe vincular al menos una incidencia");
		}
		for (Integer id : incidencias) {

			for (Incidencia inc : gestorIncidencias.getIncidencias()) {
				if (inc.getId().equals(id)) {
					flag = 1;
					incAux = inc;

					break;
				}
			}

			if (flag == 0) {
				throw new Exception("incidencia no existente");

			} else if (incAux.getProceso() != null) {
				throw new Exception("la incidencia ya ha sido asignada");

			} else {
				incidenciasAux.add(incAux);
			}
			flag = 0;

		}
		if (ordenesTrabajo!=null) {
			for (Integer id : ordenesTrabajo) {

				for (OrdenTrabajo ord : gestorOrdenesTrabajo.getOrdenesTrabajo()) {

					if (ord.getId().equals(id)) {
						flag = 1;
						otAux = ord;
						break;
					}
				}

				if (flag == 0) {
					throw new Exception("orden de trabajo no existente");

				} else if (otAux.getProceso() != null) {
					throw new Exception("la orden de trabajo ya ha sido asignada");

				} else {
					ordenesAux.add(otAux);
				}
				flag = 0;
			}
			idProcesos++;
			Proceso proc = new Proceso(idProcesos, coste, descripcion, estado, nombre, responsable, servicio,
					incidenciasAux, ordenesAux);
			procesos.add(proc);
			return proc;
		} else {
			idProcesos++;
			Proceso proc = new Proceso(idProcesos, coste, descripcion, estado, nombre, responsable, servicio, incidenciasAux);
			procesos.add(proc);
			return proc;
		}

	}

	@Override
	public void vincularIncidencias(Integer id, ArrayList<Integer> incidencias) throws Exception {
		Boolean flag = false;
		ArrayList<Incidencia> incCreadas = gestorIncidencias.getIncidencias();
		ArrayList<Incidencia> incVincular = new ArrayList<Incidencia>();
		Proceso procesoVincular = null;

		// existe el proceso?
		for (Proceso proc : procesos) {

			if (proc.getId().equals(id)) {
				flag = true;
				procesoVincular = proc;
				break;
			}
		}
		if (flag.equals(false)) {
			throw new Exception("el proceso no existe");
		}
		flag = false;

		for (Integer idInc : incidencias) {

			for (Incidencia inc : incCreadas) {
				if (inc.getId().equals(idInc) && inc.getProceso() == null) {
					flag = true;
					incVincular.add(inc);
					break;
				}
			}
			if (flag.equals(false)) {
				throw new Exception("Incidencia no valida");
			}
			flag = false;

		}
		for (Incidencia inc : incVincular) {

			procesoVincular.anadirIncidencia(inc);
			inc.setProceso(procesoVincular);

		}

	}

	@Override
	public void vincularOT(Integer id, ArrayList<Integer> ordenesTrabajo) throws Exception {
		// TODO Auto-generated method stub
		Boolean flag = false;
		ArrayList<OrdenTrabajo> otCreadas = gestorOrdenesTrabajo.getOrdenesTrabajo();
		ArrayList<OrdenTrabajo> otVincular = new ArrayList<OrdenTrabajo>();
		;
		Proceso procesoVincular = null;

		// existe el proceso?
		for (Proceso proc : procesos) {

			if (proc.getId().equals(id)) {
				flag = true;
				procesoVincular = proc;
				break;
			}
		}
		if (flag.equals(false)) {
			throw new Exception("el proceso no existe");
		}
		flag = false;

		for (Integer idOT : ordenesTrabajo) {

			for (OrdenTrabajo ot : otCreadas) {
				if (ot.getId().equals(idOT) && ot.getProceso() == null) {
					flag = true;
					otVincular.add(ot);
					break;
				}
			}
			if (flag.equals(false)) {
				throw new Exception("Orden de trabajo no valida");
			}
			flag = false;

		}
		for (OrdenTrabajo ot : otVincular) {

			procesoVincular.anadirOT(ot);
			ot.setProceso(procesoVincular);

		}

	}

	@Override
	public void desvincularIncidencias(Integer id, ArrayList<Integer> incidencias) throws Exception {
		// TODO Auto-generated method stub
		Boolean flag = false;
		Proceso procesoDesvincular = null;
		ArrayList<Incidencia> incidenciasDesvincular = new ArrayList<Incidencia>();
		
		if(incidencias==null || id==null) {
			throw new Exception("los argumentos no pueden ser null");
		}
		if(incidencias.isEmpty()) {
			throw new Exception("incidencias no validas");
		}
		
		// existe el proceso?
		for (Proceso proc : procesos) {

			if (proc.getId().equals(id)) {
				flag = true;
				procesoDesvincular = proc;
				break;
			}
		}
		if (flag.equals(false)) {
			throw new Exception("el proceso no existe");
		}
		flag = false;
		for (Integer idInc : incidencias) {
			for (Incidencia inc : procesoDesvincular.getIncidencias()) {

				if (inc.getId().equals(idInc)) {
					flag = true;
					incidenciasDesvincular.add(inc);
					break;
				}
			}

			if (flag.equals(false)) {

				throw new Exception("incidencias incorrectas");
			}
		}

		for (Incidencia inc : incidenciasDesvincular) {

			inc.setProceso(null);
			procesoDesvincular.eliminarIncidencia(inc.getId());
		}

	}

	@Override
	public void desvincularOT(Integer id, ArrayList<Integer> ordenesTrabajo) throws Exception {
		// TODO Auto-generated method stub
		Boolean flag = false;
		Proceso procesoDesvincular = null;
		ArrayList<OrdenTrabajo> ordenesDesvincular = new ArrayList<OrdenTrabajo>();
		// existe el proceso?
		for (Proceso proc : procesos) {

			if (proc.getId().equals(id)) {
				flag = true;
				procesoDesvincular = proc;
				break;
			}
		}
		if (flag.equals(false)) {
			throw new Exception("el proceso no existe");
		}
		flag = false;
		for (Integer idOT : ordenesTrabajo) {
			for (OrdenTrabajo ot : procesoDesvincular.getOrdenesTrabajo()) {

				if (ot.getId().equals(idOT)) {
					flag = true;
					ordenesDesvincular.add(ot);
					break;
				}
			}

			if (flag.equals(false)) {

				throw new Exception("ordenes de trabajo incorrectas");
			}
		}

		for (OrdenTrabajo ot : ordenesDesvincular) {

			ot.setProceso(null);
			procesoDesvincular.eliminarOT(ot.getId());
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<Proceso> filtrarProcesos(HashMap<String, Object> filtros) {
		
	
		
		if(filtros==null) {
			return null;
		}
		if(filtros.isEmpty()) {
			return procesos;
		}
		for(String key: filtros.keySet()) {
			switch(key) {
				case "responsable":
					if(filtros.get(key)!=null && !filtros.get(key).getClass().equals(("").getClass())) return null;
			
					break;
				case "incidencia":
					if(filtros.get(key)!=null && !filtros.get(key).getClass().equals(Integer.valueOf(0).getClass())) return null;
					
					break;
				case "estado":
					if(filtros.get(key)!=null && !filtros.get(key).getClass().equals(("").getClass())) return null;
							
					break;
				case "fecha":
					if(filtros.get(key)!=null && !filtros.get(key).getClass().equals(new Date().getClass())) return null;
			
					break;
				case "orden de trabajo":
					if(filtros.get(key)!=null && !filtros.get(key).getClass().equals(Integer.valueOf(0).getClass())) return null;
			
					break;
				default:
					return null;
			}
		}
		

		ArrayList<Proceso> procesosFiltrados = new ArrayList<Proceso>();
		ArrayList<Boolean> filtrosUsados = new ArrayList<Boolean>();// orden: responsable, incidencia, estado, fecha,
																	// orden de trabajo
		filtrosUsados.add(true);
		String responsable = null, estado = null;
		Date fecha = null;
		Integer incidencia = null, ot = null;
		try {
			responsable = (String) filtros.get("responsable");
			if (responsable == null)
				filtrosUsados.set(0, false);
		} catch (NoSuchElementException ex) {
			filtrosUsados.set(0, false);
		}
		filtrosUsados.add(true);
		try {
			incidencia = (Integer) filtros.get("incidencia");
			if (incidencia == null)
				filtrosUsados.set(1, false);
		} catch (NoSuchElementException ex) {
			filtrosUsados.set(1, false);
		}
		filtrosUsados.add(true);
		try {
			estado = (String) filtros.get("estado");
			if (estado == null)
				filtrosUsados.set(2, false);
		} catch (NoSuchElementException ex) {
			filtrosUsados.set(2, false);
		}
		filtrosUsados.add(true);
		try {
			fecha = (Date) filtros.get("fecha");
			if (fecha == null)
				filtrosUsados.set(3, false);
		} catch (NoSuchElementException ex) {
			filtrosUsados.set(3, false);
		}
		filtrosUsados.add(true);
		try {
			ot = (Integer) filtros.get("orden de trabajo");
			if (ot == null)
				filtrosUsados.set(4, false);
		} catch (NoSuchElementException ex) {
			filtrosUsados.set(4, false);
		}

		Boolean aux = false;

		for (Proceso proc : procesos) {

			if (filtrosUsados.get(0).equals(false)
					|| (filtrosUsados.get(0).equals(true) && proc.getResponsable().equals(responsable)))
				aux = true;

			if (aux.equals(true) && (filtrosUsados.get(2).equals(false)
					|| (filtrosUsados.get(2).equals(true) && proc.getEstado().equals(estado)))) {
				aux = true;

			} else {
				aux = false;
			}

			if (aux.equals(true) && (filtrosUsados.get(3).equals(false) || (filtrosUsados.get(3).equals(true)
					&& proc.getFecha().getYear() == fecha.getYear() && proc.getFecha().getMonth() == fecha.getMonth()
					&& proc.getFecha().getDay() == fecha.getMonth()))) {
				aux = true;

			} else {
				aux = false;
			}

			if (aux.equals(true) && filtrosUsados.get(1).equals(true)) {
				Boolean aux2 = false;
				for (Incidencia inc : proc.getIncidencias()) {
					if (inc.getId().equals(incidencia))
						aux2 = true;
				}
				if (aux2.equals(false))
					aux = false;

			}
			if (aux.equals(true) && filtrosUsados.get(4).equals(true)) {
				Boolean aux2 = false;
				for (OrdenTrabajo ord : proc.getOrdenesTrabajo()) {
					if (ord.getId().equals(ot))
						aux2 = true;
				}
				if (aux2.equals(false))
					aux = false;

			}
			if (aux.equals(true))
				procesosFiltrados.add(proc);

		}

		return procesosFiltrados;
	}

	@Override
	public ArrayList<Proceso> filtrarSinAsignarOT() {
		// TODO Auto-generated method stub
		ArrayList<Proceso> procesosFiltrados = new ArrayList<Proceso>();
		for (Proceso proc : procesos) {
			if (proc.getOrdenesTrabajo().isEmpty()) {
				procesosFiltrados.add(proc);
			}
		}
		return procesosFiltrados;
	}

	@Override
	public void modificarProceso(Proceso proceso) throws Exception {

		int flag = 0;
		Proceso procActual = null;

		for (Proceso proc : procesos) {
			if (proc.getId().equals(proceso.getId())) {
				flag = 1;
				procActual = proc;
				break;
			}
		}
		if (flag == 0) {
			throw new Exception("proceso no existente");
		}

		if (proceso.getNombre() == null || proceso.getEstado() == null || proceso.getResponsable() == null
				|| proceso.getServicio() == null || proceso.getDescripcion() == null
				|| proceso.getCosteEstimado() == null) {
			throw new Exception("ningún campo puede ser nulo");
		}

		if (proceso.getNombre().length() > 20) {
			throw new Exception("nombre demasiado largo");
		}
		if (!(proceso.getEstado().equals("sin comenzar") || proceso.getEstado().equals("planificado")
				|| proceso.getEstado().equals("en construcción") || proceso.getEstado().equals("finalizado"))) {

			throw new Exception("estado incorrecto");
		}
		if (proceso.getResponsable().length() > 40) {
			throw new Exception("responsable demasiado largo");
		}
		if (!(proceso.getServicio().equals("Suministro") || proceso.getServicio().equals("Limpieza")
				|| proceso.getServicio().equals("Urbanística") || proceso.getServicio().equals("Legalidad")
				|| proceso.getServicio().equals("Otros"))) {

			throw new Exception("servicio incorrecto");
		}
		if (proceso.getDescripcion().length() > 400) {
			throw new Exception("descripción demasiado larga");
		}
		if (proceso.getCosteEstimado() <= 0) {
			throw new Exception("el coste debe ser un número positivo");
		}
		procActual.setNombre(proceso.getNombre());
		procActual.setEstado(proceso.getEstado());
		procActual.setResponsable(proceso.getResponsable());
		procActual.setServicio(proceso.getServicio());
		procActual.setDescripcion(proceso.getDescripcion());
		procActual.setCosteEstimado(proceso.getCosteEstimado());

	}

}
