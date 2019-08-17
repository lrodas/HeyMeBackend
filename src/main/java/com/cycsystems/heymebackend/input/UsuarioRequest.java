package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Usuario;

public class UsuarioRequest extends BaseInput {

	private Usuario datos;

	public Usuario getDatos() {
		return datos;
	}

	public void setDatos(Usuario datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "UsuarioRequest [datos=" + datos + ", toString()=" + super.toString() + "]";
	}
}
