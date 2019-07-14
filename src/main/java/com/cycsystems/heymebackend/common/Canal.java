package com.cycsystems.heymebackend.common;

public class Canal {

	private Integer idCanal;
	private String nombre;

	public Canal(Integer idCanal, String nombre) {
		this.idCanal = idCanal;
		this.nombre = nombre;
	}

	public Canal() {
	}

	public Integer getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(Integer idCanal) {
		this.idCanal = idCanal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Canal [idCanal=" + idCanal + ", nombre=" + nombre + "]";
	}
}
