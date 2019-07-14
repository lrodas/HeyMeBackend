package com.cycsystems.heymebackend.common;

public class Contacto {

	private Integer idContacto;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private Region region;
	private String direccion;
	private Boolean estado;

	public Contacto() {
	}

	public Contacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	public Integer getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Contacto [idContacto=" + idContacto + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono="
				+ telefono + ", email=" + email + ", region=" + region + ", direccion=" + direccion + ", estado="
				+ estado + "]";
	}
}
