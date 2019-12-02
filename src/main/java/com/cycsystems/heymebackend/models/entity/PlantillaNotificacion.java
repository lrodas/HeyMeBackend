package com.cycsystems.heymebackend.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plantillaNotificacion")
@Getter
@Setter
@ToString
public class PlantillaNotificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPlantillaNotificacion;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "plantilla", nullable = false, columnDefinition = "TEXT")
	private String plantilla;

	@Column(name = "estado", columnDefinition = "TINYINT DEFAULT 1")
	private Boolean estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCanal")
	private Canal canal;

	public PlantillaNotificacion(Integer idPlantillaNotificacion, String titulo, String plantilla, Boolean estado) {
		this.idPlantillaNotificacion = idPlantillaNotificacion;
		this.titulo = titulo;
		this.plantilla = plantilla;
		this.estado = estado;
	}

	public PlantillaNotificacion() {
	}

	private static final long serialVersionUID = 1L;

}
