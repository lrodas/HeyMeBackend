package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

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
@Table(name = "EstadoPaqueteConsumo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EstadoPaqueteConsumo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEstadoPaqueteConsumo;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	public EstadoPaqueteConsumo(Integer estadoPaqueteConsumoActivo) {
		this.idEstadoPaqueteConsumo = estadoPaqueteConsumoActivo;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
