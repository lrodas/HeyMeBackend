package com.cycsystems.heymebackend.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipoOperacion")
@Data
public class TipoOperacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoOperacion;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	public TipoOperacion() {
	}

	public TipoOperacion(Integer idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	private static final long serialVersionUID = 1L;
}
