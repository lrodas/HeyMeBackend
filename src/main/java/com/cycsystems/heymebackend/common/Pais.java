package com.cycsystems.heymebackend.common;

public class Pais {

	private Integer idPais;
	private String nombre;

	public Pais(Integer idPais, String nombre) {
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public Pais() {
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Pais [idPais=" + idPais + ", nombre=" + nombre + "]";
	}
}
