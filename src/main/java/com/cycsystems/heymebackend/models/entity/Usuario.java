package com.cycsystems.heymebackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario",uniqueConstraints= {@UniqueConstraint(columnNames= {"username"})})
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	private String nombres;
	
	private String apellidos;
	
	private String direccion;
	
	private String telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idRole")
	private Role role;
	
	@Column(length = 30, unique = true)
	private String username;

	@Column(length = 60)
	private String password;
	
	private String img;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Genero genero;
	
	private Boolean enabled;
	
	private Date fechaAlta;

	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;

	public Usuario(String nombres, String apellidos, String direccion, String telefono, Role role, String username, String password, String img, Genero genero, Boolean enabled, Date fechaAlta, Empresa empresa) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.role = role;
		this.username = username;
		this.password = password;
		this.img = img;
		this.genero = genero;
		this.enabled = enabled;
		this.fechaAlta = fechaAlta;
		this.empresa = empresa;
	}

	@PrePersist
	private void prePersist() {
		this.fechaAlta = new Date();
	}

	public Usuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario() {
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombres=" + nombres + ", apellidos=" + apellidos + ", direccion="
				+ direccion + ", telefono=" + telefono + ", role=" + role + ", username=" + username + ", password="
				+ password + ", img=" + img + ", genero=" + genero + ", enabled=" + enabled + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
