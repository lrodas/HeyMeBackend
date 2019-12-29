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

	public Opcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}
}
