package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Permiso;

import java.util.List;

public class PermisoRequest extends BaseInput {

	private List<Permiso> permisos;
	private String role;
	private Integer idRole;

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "PermisoRequest [permisos=" + permisos + ", role=" + role + ", idRole=" + idRole + "]";
	}
}
