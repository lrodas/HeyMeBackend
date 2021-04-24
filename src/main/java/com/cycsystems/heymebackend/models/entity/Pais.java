package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="pais")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pais implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPais;
	
	@Column(name="nombre", nullable=false)
	private String nombre;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "estado", nullable = false, columnDefinition = " TINYINT DEFAULT 1")
	private Boolean estado;

}
