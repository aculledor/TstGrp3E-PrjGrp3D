package SubsistemaGestionOrdenesTrabajo;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

public interface IGestorOT {
	public OrdenTrabajo crearOrdenTrabajo(String empresaResponsable, String estado, Integer duracionEstimada, Date fechaInicio,
			String descripcion, Float coste, String material, ArrayList<String> personal, ArrayList<Float> presupuestos,
			Integer proceso) throws Exception;

	public ArrayList<OrdenTrabajo> filtrarOT(HashMap<String, Object> filtros);

	public Float acumuladoEmpresa(String empresa) throws Exception;

	public Float acumuladoProceso(Integer proceso) throws Exception;

	public void modificarOT(OrdenTrabajo ordenTrabajo) throws Exception;
}
