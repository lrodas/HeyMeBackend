package com.cycsystems.heymebackend.common;

public abstract class BaseInput {

	private String usuario;
	private Integer idUsuario;
	private String pagina;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	@Override
	public String toString() {
		return "BaseInput [usuario=" + usuario + ", idUsuario=" + idUsuario + ", pagina=" + pagina + "]";
	}

}
