package com.cycsystems.heymebackend.output;

import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Permiso;

public class PermisoResponse extends BaseOutput {

	List<Permiso> permisos;

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	@Override
	public String toString() {
		return "PermisoResponse [permisos=" + permisos + ", toString()=" + super.toString() + "]";
	}
}
