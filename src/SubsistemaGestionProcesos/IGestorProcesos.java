package SubsistemaGestionProcesos;

import java.util.ArrayList;

import java.util.HashMap;

public interface IGestorProcesos {
	public Proceso crearProceso(String nombre, String estado, String responsable, String servicio, String descripcion, Float coste, ArrayList<Integer> incidencias, ArrayList<Integer> ordenesTrabajo) throws Exception; 

	public ArrayList<Proceso> filtrarProcesos(HashMap<String, Object> filtros); 

	public ArrayList<Proceso> filtrarSinAsignarOT(); 

	public void modificarProceso(Proceso proceso) throws Exception; 

	void vincularIncidencias(Integer id, ArrayList<Integer> incidencias) throws Exception; 

	void vincularOT(Integer id, ArrayList<Integer> ordenesTrabajo) throws Exception; 

	void desvincularIncidencias(Integer id, ArrayList<Integer> incidencias) throws Exception; 

	void desvincularOT(Integer id, ArrayList<Integer> ordenesTrabajo) throws Exception; 
}
