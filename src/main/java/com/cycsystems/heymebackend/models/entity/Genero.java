package com.cycsystems.heymebackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="genero")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genero implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idGenero;
	
	private String descripcion;
	
	public Genero(Integer idGenero, String descripcion) {
		this.idGenero = idGenero;
		this.descripcion = descripcion;
	}

	public Genero() {
	}

	public Integer getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Genero [idGenero=" + idGenero + ", descripcion=" + descripcion + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
