package com.cycsystems.heymebackend.common;

public class Opcion {

	private Integer idOpcion;
	private String descripcion;
	private String orden;
	private String url;
	private boolean evento;
	private String icono;

	public Integer getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEvento() {
		return evento;
	}

	public void setEvento(boolean evento) {
		this.evento = evento;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	@Override
	public String toString() {
		return "Opcion [idOpcion=" + idOpcion + ", descripcion=" + descripcion + ", orden=" + orden + ", url=" + url
				+ ", evento=" + evento + ", icono=" + icono + "]";
	}
}
