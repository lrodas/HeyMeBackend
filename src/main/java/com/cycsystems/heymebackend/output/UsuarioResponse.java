package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioResponse extends BaseOutput {

	private Usuario usuario;
	private List<Usuario> usuarios;

	public List<Usuario> getUsuarios() {
		if (this.usuarios == null) {
			this.usuarios = new ArrayList<>();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "UsuarioResponse [usuario=" + usuario + ", usuarios=" + usuarios + "]";
	}
}
