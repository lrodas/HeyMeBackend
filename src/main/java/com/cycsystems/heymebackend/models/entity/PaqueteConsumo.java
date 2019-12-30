package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "paqueteConsumo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaqueteConsumo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaqueteConsumo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaquete", nullable = false)
	private Paquete paquete;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa", nullable = false)
	private Empresa empresa;

	@Column(nullable = false, columnDefinition = " DATE DEFAULT '1900-01-01'")
	private Date fechaInicio;

	@Column(nullable = false, columnDefinition = " DATE DEFAULT '1900-01-01'")
	private Date fechaFin;

	@Column(name = "consumoSMS", nullable = false, columnDefinition = " Int DEFAULT 0")
	private Integer consumoSMS;
	
	@Column(nullable = false, columnDefinition = " Int DEFAULT 0")
	private Integer consumoMAIL;
	
	@Column(nullable = false, columnDefinition = " Int DEFAULT 0")
	private Integer consumoWhatsapp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoPaqueteConsumo", nullable = false)
	private EstadoPaqueteConsumo estado;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
