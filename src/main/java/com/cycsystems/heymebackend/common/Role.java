package com.cycsystems.heymebackend.common;

public class Role {

	private Integer idRole;
	private String descripcion;
	private String nombre;
	private Boolean estado;

	public Role(Integer idRole, String nombre, String descripcion, Boolean estado) {
		this.idRole = idRole;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estado = estado;
	}

	public Role() {
	}

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Role [idRole=" + idRole + ", descripcion=" + descripcion + ", nombre=" + nombre + ", estado=" + estado
				+ "]";
	}
}
