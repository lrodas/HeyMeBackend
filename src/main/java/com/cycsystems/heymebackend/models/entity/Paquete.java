package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "paquete")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Paquete implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaquete;
	private String nombre;
	private Double precio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoPaquete", nullable = false)
	private EstadoPaquete estado;
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
