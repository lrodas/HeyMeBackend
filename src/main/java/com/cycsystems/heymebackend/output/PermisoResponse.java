package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Permiso;

import java.util.List;

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
