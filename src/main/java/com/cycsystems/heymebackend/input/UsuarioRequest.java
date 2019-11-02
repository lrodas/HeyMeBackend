package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Usuario;

import java.util.Date;

public class UsuarioRequest extends BaseInput {

	private Usuario datos;
	private Date fechaInicio;
	private Date fechaFin;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Usuario getDatos() {
		return datos;
	}

	public void setDatos(Usuario datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "UsuarioRequest [datos=" + datos + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", toString()=" + super.toString() + "]";
	}
}
