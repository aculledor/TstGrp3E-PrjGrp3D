package SubsistemaAnalisisEstadisticas;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

public class GeneradorEstadisticas implements IGeneradorEstadisticas {

	private GestorOT gestorOrdenesTrabajo;
	private GestorProcesos gestorProcesos;
	private GestorIncidencias gestorIncidencias;

	public GeneradorEstadisticas() {
		gestorIncidencias = new GestorIncidencias();
		gestorOrdenesTrabajo = new GestorOT();
		gestorProcesos = new GestorProcesos(gestorIncidencias, gestorOrdenesTrabajo);
		this.gestorOrdenesTrabajo.setGestorProc(gestorProcesos);
	}

	public GeneradorEstadisticas(GestorOT gestorOrdenesTrabajo, GestorProcesos gestorProcesos,
			GestorIncidencias gestorIncidencias) {

		this.gestorOrdenesTrabajo = gestorOrdenesTrabajo;
		this.gestorProcesos = gestorProcesos;
		this.gestorIncidencias = gestorIncidencias;
	}

	public GestorOT getGestorOrdenes() {
		return gestorOrdenesTrabajo;
	}

	public GestorProcesos getGestorProcesos() {
		return gestorProcesos;
	}

	public GestorIncidencias getGestorIncidencias() {
		return gestorIncidencias;
	}

	@Override
	public Integer contarIncidencias() {
		return this.gestorIncidencias.getIncidencias().size();
	}

	@Override
	public HashMap<Date, Integer> distribucionIncidencias(Boolean dias, Boolean semanas) throws Exception {
		if(dias == null || semanas == null) {
			throw new Exception("Los valores de dias y semanas no puede ser null.");
		}
		if (dias == true && semanas == true) {
			throw new Exception("No se puede dar al mismo tiempo la distribucion por dias y semanas");				
		}
		else if (dias == false && semanas == false) {
			throw new Exception("Es necesario indicar si la distribucion es por dias o por semanas");				
		}
		else{
			
			//Código común para los dos filtros (dias y semanas)
			ArrayList<Incidencia> incidencias = this.gestorIncidencias.getIncidencias();
			ArrayList<Date> fechas = new ArrayList<Date>();
			HashMap<Date, Integer> distribucion = new HashMap<Date, Integer>();
			
			if (dias == true && semanas == false) {
				for (Incidencia incidencia : incidencias) {
					if (!this.contieneFecha(fechas, incidencia.getFechaReporte())) {
						fechas.add(incidencia.getFechaReporte());
					}
				}
				for (Date fecha : fechas) {
					Integer cuenta = 0;
					for (Incidencia incidencia : incidencias) {
						if(incidencia.getFechaReporte().getYear() == fecha.getYear() && incidencia.getFechaReporte().getMonth() == fecha.getMonth() && incidencia.getFechaReporte().getDate() == fecha.getDate()) {
							cuenta++;
						}
					}
					distribucion.put(fecha, cuenta);
				}
			}
			else {	// Filtro por semanas     (dias == false && semanas == true)
				
				// Hay que ordenar las incidencias por fecha
				Integer tam = incidencias.size();
				ArrayList<Incidencia> incidencias_ordenadas = new ArrayList<Incidencia>();
				Incidencia incidencia_menor;
				for(int i = 0; i < tam; i++) {
					incidencia_menor = incidencias.get(0);
					for (Incidencia incidencia : incidencias) {
						if(incidencia_menor.getFechaReporte().compareTo(incidencia.getFechaReporte()) >= 0){
							incidencia_menor = incidencia;
						}
					}
					incidencias.remove(incidencia_menor);
					incidencias.add(incidencia_menor);
				}
				
				Incidencia primera_inc_sem_actual = incidencias_ordenadas.get(0);
				distribucion.put(primera_inc_sem_actual.getFechaReporte(), 1);
				for(int i = 1; i < tam; i++) {
					if(incidencias_ordenadas.get(i).getFechaReporte().getDay() >= primera_inc_sem_actual.getFechaReporte().getDay()) {	// Si seguimos en la misma semana
						distribucion.put(primera_inc_sem_actual.getFechaReporte(), distribucion.get(primera_inc_sem_actual.getFechaReporte()) + 1);
					} else {
						primera_inc_sem_actual = incidencias_ordenadas.get(i);
						distribucion.put(primera_inc_sem_actual.getFechaReporte(), 1);
					}
				}
			}
			return distribucion;
		}
	}

	@Override
	public Integer contarProcesos(String responsable) throws Exception {
		
		
		if (responsable!=null && responsable.length() > 40) {
			throw new Exception("responsable demasiado largo");
		}
		HashMap<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("responsable", responsable);
		return this.gestorProcesos.filtrarProcesos(filtro).size();
	}

	@Override
	public HashMap<Date, Integer> distribucionProcesos(Boolean dias, Boolean semanas, String responsable)
			throws Exception {
		if(dias == null || semanas == null) {
			throw new Exception("Los valores de dias y semanas no puede ser null.");
		}
		if (responsable != null && responsable.length() > 40) {
			throw new Exception("responsable demasiado largo");
		}
		else {
			if (dias == true && semanas == true) {
				throw new Exception("No se puede dar al mismo tiempo la distribucion por dias y semanas");				
			}
			else if (dias == false && semanas == false) {
				throw new Exception("Es necesario indicar si la distribucion es por dias o por semanas");				
			}
			else{		//Código común para las dos distribuciones (días y semanas)
				
				HashMap<String, Object> filtro = new HashMap<String, Object>();
				filtro.put("responsable", responsable);
				ArrayList<Proceso> procesos = this.gestorProcesos.filtrarProcesos(filtro);
				ArrayList<Date> fechas = new ArrayList<Date>();
				HashMap<Date, Integer> distribucion = new HashMap<Date, Integer>();
				
				// Distinguimos ahora para cada una de las dos distribuciones
				
				if (dias == true && semanas == false) {
					for (Proceso proceso : procesos) {
						if (!this.contieneFecha(fechas, proceso.getFecha())) {
							fechas.add(proceso.getFecha());
						}
					}
					for (Date fecha : fechas) {
						Integer cuenta = 0;
						for (Proceso proceso : procesos) {
							if(proceso.getFecha().getYear() == fecha.getYear() && proceso.getFecha().getMonth() == fecha.getMonth() && proceso.getFecha().getDate() == fecha.getDate()) {
								cuenta++;
							}
						}
						distribucion.put(fecha, cuenta);
					}
				}
				else {	// Filtro por semanas     (dias == false && semanas == true)
					
					// Hay que ordenar los procesos por fecha
					Integer tam = procesos.size();
					ArrayList<Proceso> procesos_ordenados = new ArrayList<Proceso>();
					Proceso proceso_menor;
					for(int i = 0; i < tam; i++) {
						proceso_menor = procesos.get(0);
						for (Proceso proceso : procesos) {
							if(proceso_menor.getFecha().compareTo(proceso.getFecha()) >= 0){
								proceso_menor = proceso;
							}
						}
						procesos.remove(proceso_menor);
						procesos_ordenados.add(proceso_menor);
					}
					
					Proceso primer_proc_sem_actual = procesos_ordenados.get(0);
					distribucion.put(primer_proc_sem_actual.getFecha(), 1);
					for(int i = 1; i < tam; i++) {
						if(procesos_ordenados.get(i).getFecha().getDay() >= primer_proc_sem_actual.getFecha().getDay()) {	// Si seguimos en la misma semana
							distribucion.put(primer_proc_sem_actual.getFecha(), distribucion.get(primer_proc_sem_actual.getFecha()) + 1);
						} else {
							primer_proc_sem_actual = procesos_ordenados.get(i);
							distribucion.put(primer_proc_sem_actual.getFecha(), 1);
						}
					}
				}
				return distribucion;
			}
		}
	}

	@Override
	public Integer contarOT(String empresaResponsable) throws Exception {
		if (empresaResponsable!=null && empresaResponsable.length() > 30) {
			throw new Exception("empresa responsable no valida");
		}
		HashMap<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("empresa", empresaResponsable);
		return this.gestorOrdenesTrabajo.filtrarOT(filtro).size();
	}

	@Override
	public HashMap<Date, Integer> distribucionOT(Boolean dias, Boolean semanas, String empresaResponsable)
			throws Exception {
		if(dias == null || semanas == null) {
			throw new Exception("Los valores de dias y semanas no puede ser null.");
		}
		if(empresaResponsable!=null && empresaResponsable.length() > 30) {
			throw new Exception("empresa responsable no valida");
		}
		else {
			if (dias == true && semanas == true) {
				throw new Exception("No se puede dar al mismo tiempo la distribucion por dias y semanas");				
			}
			else if (dias == false && semanas == false) {
				throw new Exception("Es necesario indicar si la distribucion es por dias o por semanas");				
			}
			else{		// Código común para las dos distribuciones (días y semanas)
				
				
				// Filtramos las ordenes de trabajo por empresa responsable
				HashMap<String, Object> filtro = new HashMap<String, Object>();
				filtro.put("empresa", empresaResponsable);
				ArrayList<OrdenTrabajo> ordenes = this.gestorOrdenesTrabajo.filtrarOT(filtro);
				
				ArrayList<Date> fechas = new ArrayList<Date>();
				HashMap<Date, Integer> distribucion = new HashMap<Date, Integer>();
				
				// Distinguimos ahora para cada una de las dos distribuciones
				
				if (dias == true && semanas == false) {		// Distribucion por dias
					
					// Obtenemos una lista de fechas en las que hay ordenes de trabajo
					for (OrdenTrabajo orden : ordenes) {
						if (!this.contieneFecha(fechas, orden.getFechaInicio())) {
							fechas.add(orden.getFechaInicio());
						}
					}
					
					// Para cada una de las fechas obtenidas contamos las ordenes de trabajo
					for (Date fecha : fechas) {
						Integer cuenta = 0;
						for (OrdenTrabajo orden : ordenes) {
							// Comparamos los campos año, mes y dia para ver si las fechas son la misma
							if(orden.getFechaInicio().getYear() == fecha.getYear() && orden.getFechaInicio().getMonth() == fecha.getMonth() && orden.getFechaInicio().getDate() == fecha.getDate()) {
								cuenta++;
							}
						}
						distribucion.put(fecha, cuenta);	// Colocamos en el hashmap el par correspondiente
					}
				}
				else {	// Distribucion por semanas     (dias == false && semanas == true)
					
					// Ordenamos las OOTT por fecha
					Integer tam = ordenes.size();
					ArrayList<OrdenTrabajo> ordenes_ordenadas = new ArrayList<OrdenTrabajo>();
					OrdenTrabajo orden_menor;
					for(int i = 0; i < tam; i++) {
						orden_menor = ordenes.get(0);
						for (OrdenTrabajo orden : ordenes) {
							if(orden_menor.getFechaInicio().compareTo(orden.getFechaInicio()) >= 0){
								orden_menor = orden;
							}
						}
						ordenes.remove(orden_menor);
						ordenes_ordenadas.add(orden_menor);
					}
					
					// Ahora que ya disponemos de las OOTT ordenadas por fecha,
					// contaremos las OOTT de cada semana
					
					// (En el hashmap las clave de una semana será la fecha de la primera OT de esa semana)
					
					// Comenzamos obteniendo la primera OT de la primera semana
					OrdenTrabajo primera_ord_sem_actual = ordenes_ordenadas.get(0);
					// La almacenamos en el hashmap, poniendo la cuenta de esa semana a 1
					distribucion.put(primera_ord_sem_actual.getFechaInicio(), 1);
					// Iteramos para el resto de OOTT
					for(int i = 1; i < tam; i++) {
						// getDay() devuelve el dia de la semana correspondiente a una fecha dada,
						// así que lo utilizamos para ver si seguimos o no en la misma semana
						if(ordenes_ordenadas.get(i).getFechaInicio().getDay() >= primera_ord_sem_actual.getFechaInicio().getDay()) {
							// Si seguimos en la misma semana,
							// Actualizamos la entrada correspondiente del hashmap aumentando la cuenta de esa semana en 1
							distribucion.put(primera_ord_sem_actual.getFechaInicio(), distribucion.get(primera_ord_sem_actual.getFechaInicio()) + 1);
						} else {	// Si se ha cambiado de semana
							// Actualizamos la primera OT de la semana actual
							primera_ord_sem_actual = ordenes_ordenadas.get(i);
							// Añadimos al hashmap la entrada correspondiente
							distribucion.put(primera_ord_sem_actual.getFechaInicio(), 1);
						}
					}
				}
				return distribucion;
			}
		}
	}

	@Override
	public Float mediaCosteOT(String empresaResponsable) throws Exception {
		if (empresaResponsable!=null && empresaResponsable.length() > 30) {
			throw new Exception("empresa responsable no valida");
		} else {
			// Filtramos las OOTT por empresa responsable
			HashMap<String, Object> filtro = new HashMap<String, Object>();
			filtro.put("empresa", empresaResponsable);
			ArrayList<OrdenTrabajo> ordenes = this.gestorOrdenesTrabajo.filtrarOT(filtro);
			
			// Calculamos la media de los costes
			Float total = (float) 0;
			for (OrdenTrabajo orden : ordenes) {
				total += orden.getCoste();
			}
			return total / ordenes.size();
		}
	}

	@Override
	public HashMap<Date, Float> distribucionMediaCosteOT(Boolean dias, Boolean semanas, String empresaResponsable)
			throws Exception {
		if(dias == null || semanas == null) {
			throw new Exception("Los valores de dias y semanas no puede ser null.");
		}
		if (empresaResponsable!=null && empresaResponsable.length() > 30) {
			throw new Exception("empresa responsable no valida");
		}

		else {
			if (dias == true && semanas == true) {
				throw new Exception("No se puede dar al mismo tiempo la distribucion por dias y semanas");				
			}
			else if (dias == false && semanas == false) {
				throw new Exception("Es necesario indicar si la distribucion es por dias o por semanas");				
			}
			else{
				//Código común para los dos filtros (dias y semanas)
				HashMap<String, Object> filtro = new HashMap<String, Object>();
				filtro.put("empresa", empresaResponsable);
				ArrayList<OrdenTrabajo> ordenes = this.gestorOrdenesTrabajo.filtrarOT(filtro);
				ArrayList<Date> fechas = new ArrayList<Date>();
				HashMap<Date, Float> distribucion = new HashMap<Date, Float>();
				
				if (dias == true && semanas == false) {
					for (OrdenTrabajo orden : ordenes) {
						if (!this.contieneFecha(fechas, orden.getFechaInicio())) {
							fechas.add(orden.getFechaInicio());
						}
					}
					for (Date fecha : fechas) {
						Float total = (float)0.0;
						Integer num_ordenes_fecha = 0;
						for (OrdenTrabajo orden : ordenes) {
							if(orden.getFechaInicio().getYear() == fecha.getYear() && orden.getFechaInicio().getMonth() == fecha.getMonth() && orden.getFechaInicio().getDate() == fecha.getDate()) {
								total += orden.getCoste();
								num_ordenes_fecha++;
							}
						}
						distribucion.put(fecha, total/num_ordenes_fecha);
					}
				}
				else {	// dias == false && semanas == true
					// Hay que ordenar las ordenes de trabajo por fecha
					
					Integer tam = ordenes.size();
					ArrayList<OrdenTrabajo> ordenes_ordenadas = new ArrayList<OrdenTrabajo>();
					OrdenTrabajo orden_menor;
					for(int i = 0; i < tam; i++) {
						orden_menor = ordenes.get(0);
						for (OrdenTrabajo orden : ordenes) {
							if(orden_menor.getFechaInicio().compareTo(orden.getFechaInicio()) >= 0){
								orden_menor = orden;
							}
						}
						ordenes.remove(orden_menor);
						ordenes_ordenadas.add(orden_menor);
					}
					
					OrdenTrabajo primera_ord_sem_actual = ordenes_ordenadas.get(0);
					Float costeAcumulado = primera_ord_sem_actual.getCoste();
					Integer num_ord_semana_actual = 1;
					for(int i = 1; i < tam; i++) {
						if(ordenes_ordenadas.get(i).getFechaInicio().getDay() >= primera_ord_sem_actual.getFechaInicio().getDay()) {	// Si seguimos en la misma semana
							num_ord_semana_actual++;
							costeAcumulado += ordenes_ordenadas.get(i).getCoste();
						} else {
							distribucion.put(primera_ord_sem_actual.getFechaInicio(), costeAcumulado/num_ord_semana_actual);
							primera_ord_sem_actual = ordenes_ordenadas.get(i);
						}
					}
					distribucion.put(primera_ord_sem_actual.getFechaInicio(), costeAcumulado/num_ord_semana_actual);
				}
				return distribucion;
			}
		}
	}
	
	// Utilizamos esto ya que el contains correspondiente usará el equals de fecha, que no nos sirve
	boolean contieneFecha(ArrayList<Date> lista, Date fecha) {
		for(Date date : lista) {
			if(date.getYear() == fecha.getYear() && date.getMonth() == fecha.getMonth() && date.getDate() == fecha.getDate()) {
				return true;
			}
		}
		return false;
	}

}
