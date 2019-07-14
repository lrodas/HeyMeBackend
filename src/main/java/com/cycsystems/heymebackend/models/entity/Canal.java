package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="canal")
public class Canal implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCanal;
	private String nombre;
	
	public Canal(Integer idCanal, String nombre) {
		this.idCanal = idCanal;
		this.nombre = nombre;
	}

	public Canal() {
	}

	public Integer getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(Integer idCanal) {
		this.idCanal = idCanal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Canal [idCanal=" + idCanal + ", nombre=" + nombre + "]";
	}

	private static final long serialVersionUID = 1L;

}
