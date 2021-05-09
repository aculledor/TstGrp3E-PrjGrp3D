package SubsistemaGestionOrdenesTrabajo;

import java.util.ArrayList;

import java.util.Date;

import SubsistemaGestionProcesos.Proceso;

public class OrdenTrabajo {
	private Integer id;
	private Float coste;
	private String descripcion;
	private Integer duracionEstimada;
	private String empresaResponsable;
	private String estado;
	private Date fechaInicio;
	private String material;
	private ArrayList<String> personal;
	private ArrayList<Float> presupuestos;
	private Proceso proceso;
	
	
	
	
	public OrdenTrabajo(Integer id, Float coste, String descripcion, Integer duracionEstimada,
			String empresaResponsable, String estado, Date fechaInicio, String material, ArrayList<String> personal,
			ArrayList<Float> presupuestos) {
		this.id = id;
		this.coste = coste;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.empresaResponsable = empresaResponsable;
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.material = material;
		this.personal = personal;
		this.presupuestos = presupuestos;
	}
	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Float getCoste() {
		return coste;
	}
	
	public void setCoste(Float coste) {
		this.coste = coste;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}
	
	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}
	
	public String getEmpresaResponsable() {
		return empresaResponsable;
	}
	
	public void setEmpresaResponsable(String empresaResponsable) {
		this.empresaResponsable = empresaResponsable;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getMaterial() {
		return material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public ArrayList<String> getPersonal() {
		return personal;
	}
	
	public void setPersonal(ArrayList<String> personal) {
		this.personal = personal;
	}
	
	public ArrayList<Float> getPresupuestos() {
		return presupuestos;
	}
	
	public void setPresupuestos(ArrayList<Float> presupuestos) {
		this.presupuestos = presupuestos;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
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
		OrdenTrabajo other = (OrdenTrabajo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
