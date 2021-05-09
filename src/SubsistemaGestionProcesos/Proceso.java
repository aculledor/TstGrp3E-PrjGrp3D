package SubsistemaGestionProcesos;

import java.util.ArrayList;

import java.util.Date;

import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaIncidencias.Incidencia;

public class Proceso {
	private Integer id;
	private Float costeEstimado;
	private String descripcion;
	private String estado;
	private String nombre;
	private String responsable;
	private String servicio;
	private Date fecha;
	private ArrayList<Incidencia> incidencias;
	private ArrayList<OrdenTrabajo> ordenesTrabajo;
	
	

	public Proceso(Integer id, Float costeEstimado, String descripcion, String estado, String nombre,
			String responsable, String servicio, Date fecha) {
		super();
		this.id = id;
		this.costeEstimado = costeEstimado;
		this.descripcion = descripcion;
		this.estado = estado;
		this.nombre = nombre;
		this.responsable = responsable;
		this.servicio = servicio;
		this.fecha = fecha;
	}

	public Proceso(Integer id, Float costeEstimado, String descripcion, String estado, String nombre,
			String responsable, String servicio, ArrayList<Incidencia> incidencias,
			ArrayList<OrdenTrabajo> ordenesTrabajo) {
		this.id = id;
		this.costeEstimado = costeEstimado;
		this.descripcion = descripcion;
		this.estado = estado;
		this.nombre = nombre;
		this.responsable = responsable;
		this.servicio = servicio;
		this.incidencias = incidencias;
		this.ordenesTrabajo = ordenesTrabajo;
		this.fecha = new Date();
		for (Incidencia inc : incidencias) {
			inc.setProceso(this);
		}
		for (OrdenTrabajo ot : ordenesTrabajo) {
			ot.setProceso(this);
		}
	}

	public Proceso(Integer id, Float costeEstimado, String descripcion, String estado, String nombre,
			String responsable, String servicio, ArrayList<Incidencia> incidencias) {
		this.id = id;
		this.costeEstimado = costeEstimado;
		this.descripcion = descripcion;
		this.estado = estado;
		this.nombre = nombre;
		this.responsable = responsable;
		this.servicio = servicio;
		this.incidencias = incidencias;
		this.ordenesTrabajo = new ArrayList<OrdenTrabajo>();
		this.fecha = new Date();
		for (Incidencia inc : incidencias) {
			inc.setProceso(this);
		}
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(ArrayList<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public ArrayList<OrdenTrabajo> getOrdenesTrabajo() {
		return ordenesTrabajo;
	}

	public void setOrdenesTrabajo(ArrayList<OrdenTrabajo> ordenesTrabajo) {
		this.ordenesTrabajo = ordenesTrabajo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getCosteEstimado() {
		return costeEstimado;
	}

	public void setCosteEstimado(Float costeEstimado) {
		this.costeEstimado = costeEstimado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public void anadirIncidencia(Incidencia incidencia) {
		this.incidencias.add(incidencia);
	}

	public void anadirOT(OrdenTrabajo ot) {
		this.ordenesTrabajo.add(ot);
	}

	public void eliminarIncidencia(Integer id) {
		for (int i = 0; i < incidencias.size(); i++) {

			if (incidencias.get(i).getId().equals(id)) {
				incidencias.remove(i);
			}
		}
	}

	public void eliminarOT(Integer id) {
		for (int i = 0; i < ordenesTrabajo.size(); i++) {

			if (ordenesTrabajo.get(i).getId().equals(id)) {
				ordenesTrabajo.remove(i);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proceso other = (Proceso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
