package SubsistemaIncidencias;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

public interface IGestorIncidencias {
	
	public Incidencia crearIncidencia(String ciudadano, String dni, String telefono, Date fechaReporte, String descripcion, 	String localizacion, String tipo) throws Exception; 

	public ArrayList<Incidencia> filtrarIncidencias(HashMap<String, Object> filtros); 

	public ArrayList<Incidencia> filtrarSinAsignar(); 

	public void modificarIncidencia(Incidencia incidencia) throws Exception; 
}
