package com.cycsystems.heymebackend.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
