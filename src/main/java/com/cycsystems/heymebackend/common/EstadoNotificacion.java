package com.cycsystems.heymebackend.common;

public class EstadoNotificacion {

	private Integer idEstadoNotificacion;
	private String descripcion;

	public EstadoNotificacion(Integer idEstadoNotificacion, String descripcion) {
		this.idEstadoNotificacion = idEstadoNotificacion;
		this.descripcion = descripcion;
	}

	public EstadoNotificacion() {
	}

	public Integer getIdEstadoNotificacion() {
		return idEstadoNotificacion;
	}

	public void setIdEstadoNotificacion(Integer idEstadoNotificacion) {
		this.idEstadoNotificacion = idEstadoNotificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "EstadoNotificacion [idEstadoNotificacion=" + idEstadoNotificacion + ", descripcion=" + descripcion
				+ ", toString()=" + super.toString() + "]";
	}
}
