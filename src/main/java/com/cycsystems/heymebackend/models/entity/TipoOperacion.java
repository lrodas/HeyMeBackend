package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipoOperacion")
public class TipoOperacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoOperacion;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	public TipoOperacion(Integer idTipoOperacion, String nombre) {
		this.idTipoOperacion = idTipoOperacion;
		this.nombre = nombre;
	}

	public TipoOperacion(Integer idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	public Integer getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(Integer idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "TipoOperacion [idTipoOperacion=" + idTipoOperacion + ", nombre=" + nombre + "]";
	}

	private static final long serialVersionUID = 1L;

}
