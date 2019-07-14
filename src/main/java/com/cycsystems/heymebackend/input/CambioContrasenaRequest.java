package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;

public class CambioContrasenaRequest extends BaseInput {

	private String contrasenaActual;
	private String nuevaContrasena;
	private Integer idUsuario;

	public String getContrasenaActual() {
		return contrasenaActual;
	}

	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}

	public String getNuevaContrasena() {
		return nuevaContrasena;
	}

	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "CambioContrasenaRequest [contrasenaActual=" + contrasenaActual + ", nuevaContrasena=" + nuevaContrasena
				+ ", idUsuario=" + idUsuario + ", toString()=" + super.toString() + "]";
	}
}
