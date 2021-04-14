package com.cycsystems.heymebackend.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tipoCambio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TipoCambio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoCambio;

	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "estado")
	private Boolean estado;

	@PrePersist
	private void prePersist() {
		this.fecha = new Date();
	}

}
