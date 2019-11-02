package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="region")
public class Region implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRegion;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPais")
	private Pais pais;

	public Region(Integer idRegion, String nombre, Pais pais) {
		this.idRegion = idRegion;
		this.nombre = nombre;
		this.pais = pais;
	}

	public Region() {
	}

	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Region [idRegion=" + idRegion + ", nombre=" + nombre + ", pais=" + pais + "]";
	}

	private static final long serialVersionUID = 1L;

}
