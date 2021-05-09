package SubsistemaIncidencias;

import java.util.Date;


import SubsistemaGestionProcesos.Proceso;

public class Incidencia {
	
	private Integer id;
	private String ciudadano;
	private String descripcion;
	private String dni;
	private String localizacion;
	private String telefono;
	private String tipo;
	private Proceso proceso;
	private Date fechaReporte;
	
	
	public Incidencia(Integer id, String ciudadano, String descripcion, String dni, String localizacion, String telefono,
			String tipo, Date fechaReporte) {
		this.id = id;
		this.ciudadano = ciudadano;
		this.descripcion = descripcion;
		this.dni = dni;
		this.localizacion = localizacion;
		this.telefono = telefono;
		this.tipo = tipo;
		this.fechaReporte = fechaReporte;
		this.proceso = null;
	}
	
	//Todos los métodos tienen sus setter y getters a excepción de id, que no tiene setter, ya
	//que su valor no se va a modificar.
	
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCiudadano() {
		return ciudadano;
	}
	
	public void setCiudadano(String ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getLocalizacion() {
		return localizacion;
	}
	
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Date getFechaReporte() {
		return fechaReporte;
	}
	
	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
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
		Incidencia other = (Incidencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
}
