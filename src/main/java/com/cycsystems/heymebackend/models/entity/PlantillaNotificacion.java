package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plantillaNotificacion")
@AllArgsConstructor
@NoArgsConstructor
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	private static final long serialVersionUID = 1L;

}
