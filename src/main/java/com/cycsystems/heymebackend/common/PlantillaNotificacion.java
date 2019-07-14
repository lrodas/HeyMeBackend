package com.cycsystems.heymebackend.common;

public class PlantillaNotificacion {

	private Integer idPlantillaNotificacion;
	private String titulo;
	private String plantilla;
	private Boolean estado;

	public Integer getIdPlantillaNotificacion() {
		return idPlantillaNotificacion;
	}

	public void setIdPlantillaNotificacion(Integer idPlantillaNotificacion) {
		this.idPlantillaNotificacion = idPlantillaNotificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "PlantillaNotificacion [idPlantillaNotificacion=" + idPlantillaNotificacion + ", titulo=" + titulo
				+ ", plantilla=" + plantilla + ", estado=" + estado + "]";
	}
}
