package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRole;
	
	@NotEmpty
	@Column(name="descripcion", length=60, nullable=false)
	private String descripcion;
	
	@NotEmpty
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="estado")
	private Boolean estado;

	public Role(Integer idRole, @NotEmpty String descripcion, @NotEmpty String nombre, Boolean estado) {
		this.idRole = idRole;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estado = estado;
	}

	public Role(Integer idRole) {
		this.idRole = idRole;
	}

	public Role() {
	}

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Puesto [idRole=" + idRole + ", descripcion=" + descripcion + ", nombre=" + nombre + ", estado=" + estado
				+ "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
