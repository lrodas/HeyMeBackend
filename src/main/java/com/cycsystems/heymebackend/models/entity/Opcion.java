package com.cycsystems.heymebackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="opcion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Opcion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idOpcion;
	private String descripcion;
	private String orden;
	private String url;
	private boolean evento;
	private String icono;

	@Column(name = "mostrar", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
	private boolean mostrar;

	public Opcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}
}
