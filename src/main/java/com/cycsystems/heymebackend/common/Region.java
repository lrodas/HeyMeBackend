package com.cycsystems.heymebackend.common;

public class Region {

	private Integer idRegion;
	private String nombre;
	private Pais pais;

	public Region(Integer idRegion, String nombre, Pais pais) {
		this.idRegion = idRegion;
		this.nombre = nombre;
		this.pais = pais;
	}

	public Region() {
		super();
	}

	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Region [idRegion=" + idRegion + ", nombre=" + nombre + ", pais=" + pais + "]";
	}
}
