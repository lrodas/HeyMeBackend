package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="provincia")
@Entity
public class Provincia implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idProvincia;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idRegion")
	private Region region;

	public Provincia(Integer idProvincia, String nombre, Region region) {
		this.idProvincia = idProvincia;
		this.nombre = nombre;
		this.region = region;
	}

	public Provincia() {
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Provincia [idProvincia=" + idProvincia + ", nombre=" + nombre + "]";
	}

	private static final long serialVersionUID = 1L;

}
