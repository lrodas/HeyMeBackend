package com.cycsystems.heymebackend.common;

import java.util.ArrayList;
import java.util.List;

public class Role {

	private Integer idRole;
	private String descripcion;
	private String nombre;
	private Boolean estado;
	private List<Permiso> permisos;

	public Role(Integer idRole, String descripcion, String nombre, Boolean estado, List<Permiso> permisos) {
		this.idRole = idRole;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estado = estado;
		this.permisos = permisos;
	}

	public Role() {
	}

	public Role(Integer idRole, String descripcion, String nombre, Boolean estado) {
		this.idRole = idRole;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estado = estado;
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

	public List<Permiso> getPermisos() {
		if (this.permisos == null) {
			this.permisos = new ArrayList<>();
		}
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	@Override
	public String toString() {
		return "Role [idRole=" + idRole + ", descripcion=" + descripcion + ", nombre=" + nombre + ", estado=" + estado
				+ ", permisos=" + permisos + "]";
	}
}
