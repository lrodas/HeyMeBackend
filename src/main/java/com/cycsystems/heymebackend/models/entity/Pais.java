package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pais")
public class Pais implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPais;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	public Pais(Integer idPais, String nombre) {
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public Pais() {
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Pais [idPais=" + idPais + ", nombre=" + nombre + "]";
	}

	private static final long serialVersionUID = 1L;

}
