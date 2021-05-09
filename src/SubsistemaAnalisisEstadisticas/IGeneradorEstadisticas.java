package SubsistemaAnalisisEstadisticas;

import java.util.Date;

import java.util.HashMap;

public interface IGeneradorEstadisticas {

	public Integer contarIncidencias (); 

	public HashMap <Date, Integer> distribucionIncidencias (Boolean dias, Boolean semanas) throws Exception; 

	public Integer contarProcesos (String responsable) throws Exception; 

	public HashMap <Date, Integer> distribucionProcesos (Boolean dias, Boolean semanas, String responsable) throws Exception; 

	public Integer contarOT (String empresaResponsable) throws Exception; 

	public HashMap <Date, Integer> distribucionOT (Boolean dias, Boolean semanas, String empresaResponsable) throws Exception; 

	public Float mediaCosteOT(String empresaResponsable) throws Exception; 

	public HashMap <Date, Float> distribucionMediaCosteOT (Boolean dias, Boolean semanas, String empresaResponsable) throws Exception; 

	}
