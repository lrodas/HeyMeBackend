package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="contacto")
public class Contacto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idContacto;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="apellido", nullable=false)
	private String apellido;
	
	@Column(name="telefono", nullable=false)
	private String telefono;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idProvincia")
	private Provincia provincia;
	
	@Column(name="direccion", nullable=false)
	private String direccion;
	
	@Column(name="estado", columnDefinition="TINYINT DEFAULT 1")
	private Boolean estado;
	
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
	
	@PrePersist
	private void prePersist() {
		this.fechaCreacion = new Date();
	}

	public Contacto(Integer idContacto, String nombre, String apellido, String telefono, String email,
			Provincia provincia, String direccion, Boolean estado, Date fechaCreacion) {
		this.idContacto = idContacto;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
		this.provincia = provincia;
		this.direccion = direccion;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
	}

	public Contacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	public Contacto() {
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

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Contacto [idContacto=" + idContacto + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono="
				+ telefono + ", email=" + email + ", provincia=" + provincia + ", direccion=" + direccion + ", estado="
				+ estado + ", fechaCreacion=" + fechaCreacion + "]";
	}

	private static final long serialVersionUID = 1L;
}
