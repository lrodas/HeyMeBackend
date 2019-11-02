package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="estadoNotificacion")
public class EstadoNotificacion implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idEstadoNotificacion;
	
	@Column(nullable=false)
	private String descripcion;

	public EstadoNotificacion(Integer idEstadoNotificacion, String descripcion) {
		this.idEstadoNotificacion = idEstadoNotificacion;
		this.descripcion = descripcion;
	}
	
	public EstadoNotificacion(Integer idEstadoNotificacion) {
		this.idEstadoNotificacion = idEstadoNotificacion;
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
				+ "]";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
