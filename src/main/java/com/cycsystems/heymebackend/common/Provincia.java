package com.cycsystems.heymebackend.common;

public class Provincia {

	private Integer idProvincia;
	private String nombre;
	private Region region;

	public Provincia(Integer idProvincia, String nombre, Region region) {
		this.idProvincia = idProvincia;
		this.nombre = nombre;
		this.region = region;
	}

	public Provincia() {
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Provincia [idProvincia=" + idProvincia + ", nombre=" + nombre + "]";
	}
}
