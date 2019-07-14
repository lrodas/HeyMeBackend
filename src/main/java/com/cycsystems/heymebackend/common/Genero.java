package com.cycsystems.heymebackend.common;

public class Genero {

	private Integer idGenero;
	private String descripcion;

	public Genero(Integer idGenero, String descripcion) {
		this.idGenero = idGenero;
		this.descripcion = descripcion;
	}

	public Genero() {
	}

	public Integer getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Genero [idGenero=" + idGenero + ", descripcion=" + descripcion + "]";
	}
}
