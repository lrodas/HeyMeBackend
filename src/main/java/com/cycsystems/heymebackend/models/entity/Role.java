package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="role")
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
	
	@OneToMany(mappedBy = "puesto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Permiso> permisos;

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

	public List<Permiso> getPermisos() {
		if (this.permisos == null) {
			this.permisos = new ArrayList<>();
		}
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	@Override
	public String toString() {
		return "Role [idRole=" + idRole + ", descripcion=" + descripcion + ", nombre=" + nombre + ", estado=" + estado
				+ ", permisos=" + permisos + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
