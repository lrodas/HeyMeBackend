package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plantillaNotificacion")
public class PlantillaNotificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPlantillaNotificacion;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "plantilla", nullable = false)
	private String plantilla;

	@Column(name = "estado", columnDefinition = "TINYINT DEFAULT 1")
	private Boolean estado;

	public PlantillaNotificacion(Integer idPlantillaNotificacion, String titulo, String plantilla, Boolean estado) {
		this.idPlantillaNotificacion = idPlantillaNotificacion;
		this.titulo = titulo;
		this.plantilla = plantilla;
		this.estado = estado;
	}

	public PlantillaNotificacion() {
	}

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

	private static final long serialVersionUID = 1L;

}
