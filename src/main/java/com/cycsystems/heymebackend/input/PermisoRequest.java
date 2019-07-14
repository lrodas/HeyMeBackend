package com.cycsystems.heymebackend.input;

import java.util.List;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Permiso;

public class PermisoRequest extends BaseInput {

	private List<Permiso> permisos;
	private String role;

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
		return "PermisoRequest [permisos=" + permisos + ", role=" + role + ", toString()=" + super.toString() + "]";
	}
}
