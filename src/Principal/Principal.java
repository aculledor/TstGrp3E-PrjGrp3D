package Principal;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

import SubsistemaAnalisisEstadisticas.GeneradorEstadisticas;
import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

public class Principal {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Hola mundito");

		GestorIncidencias gestorInc;
		GestorProcesos gestorProc;
		GestorOT gestorOT;
		GeneradorEstadisticas generadorEst;
		ArrayList<Incidencia> incidenciasSinAsignar;
		ArrayList<Incidencia> incidenciasFiltradas;
		ArrayList<Proceso> procesosFiltrados;
		ArrayList<OrdenTrabajo> ordenesFiltradas;
		ArrayList<Integer> idsIncidencias = new ArrayList<Integer>();
		Incidencia incidencia, incidencia2, incidencia3;
		Proceso proceso;
		OrdenTrabajo orden;
		Date fecha = new Date();

		try {
			gestorInc = new GestorIncidencias();
			gestorOT = new GestorOT();
			gestorProc = new GestorProcesos(gestorInc, gestorOT);
			gestorOT.setGestorProc(gestorProc);
			generadorEst=new GeneradorEstadisticas(gestorOT, gestorProc, gestorInc);

			// CREACION DE INCIDENCIAS
			incidencia = gestorInc.crearIncidencia("Anton", "34307053T", "653789243", new Date(), "soy una incidencia",
					"Lugo", "Limpieza");

			// OBTENCION DE LA INCIDENCIA ANTERIOR
			incidenciasSinAsignar = gestorInc.filtrarSinAsignar();
			idsIncidencias.add(incidenciasSinAsignar.get(0).getId());

			// CREACIÓN DE PROCESO CON LA INCIDENCIA ANTERIOR
			proceso = gestorProc.crearProceso("proceso", "planificado", "Manuel Timiraos", "Suministro",
					"soy un proceso", Float.valueOf(500), idsIncidencias, null);
			

			// CREACION DE ORDEN DE TRABAJO
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("49202869G");
			ArrayList<Float> presupuestos = new ArrayList<Float>();
			presupuestos.add(Float.valueOf(70));
			presupuestos.add(Float.valueOf(80));
			presupuestos.add(Float.valueOf(90));
			orden = gestorOT.crearOrdenTrabajo("Mapfre", "sin comenzar", 5, new Date(), "soy una ot",
					Float.valueOf(300), "fibra óptica", personal, presupuestos, 1);

			// FILTRADO DE INCIDENCIAS
			incidencia2 = gestorInc.crearIncidencia("Manuel", "34307053T", "653786663", new Date(),
					"soy la incidencia 2", "Lugo", "Limpieza"); // se crea una nueva incidencia, que estará sin asignar
			incidenciasSinAsignar = gestorInc.filtrarSinAsignar();

			HashMap<String, Object> filtrosInc = new HashMap<String, Object>();
			filtrosInc.put("localizacion", "Lugo");
			filtrosInc.put("tipo", "Limpieza");

			incidenciasFiltradas = gestorInc.filtrarIncidencias(filtrosInc);


			// VINCULAR INCIDENCIA

			ArrayList<Integer> idsIncidencias2 = new ArrayList<Integer>();
			idsIncidencias2.add(2);
			gestorProc.vincularIncidencias(1, idsIncidencias2);

			// DESVINCULAR INCIDENCIA
			gestorProc.desvincularIncidencias(1, idsIncidencias2);


			// FILTRADO DE PROCESOS
			Proceso proceso2 = gestorProc.crearProceso("proceso 2", "planificado", "Manuel Lopez", "Limpieza",
					"soy el proceso 2", Float.valueOf(500), idsIncidencias2, null);// se crea un nuevo proceso

			HashMap<String, Object> filtrosProc = new HashMap<String, Object>();
			filtrosProc.put("responsable", "Manuel Lopez");
			filtrosProc.put("estado", "planificado");
			filtrosProc.put("incidencia", 2);
			procesosFiltrados = gestorProc.filtrarProcesos(filtrosProc);

			// FILTRADO DE ORDENES DE TRABAJO
			OrdenTrabajo orden2 = gestorOT.crearOrdenTrabajo("Mutua", "sin comenzar", 5, new Date(), "soy una ot",
					Float.valueOf(500), "fibra óptica", personal, presupuestos, 1);
			HashMap<String, Object> filtrosOrd = new HashMap<String, Object>();
			filtrosOrd.put("empresa", "Mutua");
			filtrosOrd.put("estado", "sin comenzar");
			ordenesFiltradas = gestorOT.filtrarOT(filtrosOrd);

			
			//DESVINCULAR ORDEN DE TRABAJO			
			ArrayList<Integer> idsOrdenes = new ArrayList<Integer>();
			idsOrdenes.add(2);
			gestorProc.desvincularOT(1, idsOrdenes);
			
			//VINCULAR ORDEN DE TRABAJO
			gestorProc.vincularOT(1, idsOrdenes);
			
			//OBTENCION DE INCIDENCIAS SIN ASIGNAR
			gestorProc.desvincularIncidencias(1, idsIncidencias);//desvinculamos la incidencia 1
			incidenciasSinAsignar=gestorInc.filtrarSinAsignar();
			
			//OBTENCION DE PROCESOS SIN ÓRDENES ASIGNADAS
			ArrayList<Proceso> procesosSinOT=gestorProc.filtrarSinAsignarOT();
			
			//ACUMULADO DE COSTE POR EMPRESA
			Float acumCosteEmpresa=gestorOT.acumuladoEmpresa("Mapfre");
			
			//ACUMULADO DE COSTE POR PROCESO
			Float acumCosteProceso=gestorOT.acumuladoProceso(1);
			
			//MODIFICAR INCIDENCIA
			Incidencia incAux= new Incidencia(1, "Jose","HOLA","34307053T","Viveiro","687543263","Suministro",new Date());
			
			gestorInc.modificarIncidencia(incAux);
			
			//MODIFICAR ORDEN DE TRABAJO
			OrdenTrabajo ordenAux=new OrdenTrabajo(1, Float.valueOf(400), "arreglar farola", 7, "Arreglos Martinez", "sin comenzar", new Date(121, 3, 27),
					"pintura", personal, presupuestos);
			
			gestorOT.modificarOT(ordenAux);
			
			//MODIFICAR PROCESO
			Proceso procAux=new Proceso(1, Float.valueOf(40), "tuberias estropeadas", "finalizado", "proc1","Maria","Suministro",new Date());
			gestorProc.modificarProceso(procAux);
			
			//CONTAR INCIDENCIAS
			Integer incTotales=generadorEst.contarIncidencias();
			
			//CONTAR PROCESOS
			Integer procTotales=generadorEst.contarProcesos("Manuel Lopez");
			
			//CONTAR ORDENES DE TRABAJO
			Integer otTotales=generadorEst.contarOT("Mutua");
			
			//CALCULAR MEDIA COSTE ORDENES TRABAJO
			Float mediaOT=generadorEst.mediaCosteOT(null);
			
			//DISTRIBUCION OT
			HashMap<Date, Integer> distOt = generadorEst.distribucionOT(false, true, null);
			
			//DISTRIBUCION MEDIA COSTE
			HashMap<Date, Float> distMedia = generadorEst.distribucionMediaCosteOT(false, true, null);
			
			//DISTRIBUCION INCIDENCIAS
			HashMap<Date, Integer> distInc = generadorEst.distribucionIncidencias(true, false);
			
			//DISTRIBUCION PROCESOS
			HashMap<Date, Integer> distProceso = generadorEst.distribucionProcesos(true, false, null);
			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

}
