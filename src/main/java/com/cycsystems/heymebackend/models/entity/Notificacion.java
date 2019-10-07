package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotificaciones;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "fechaEnvio", nullable = false)
	private Date fechaEnvio;

	@Column(name = "fechaProgramacion")
	private Date fechaProgramacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoNotificacion")
	private EstadoNotificacion estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	@Column(name = "notificacion", nullable = false)
	private String notificacion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idContacto")
	private List<Contacto> destinatarios;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCanal", nullable = false)
	private Canal canal;

	@PrePersist
	private void prePersist() {
		this.fechaProgramacion = new Date();
	}

	public Notificacion(Long idNotificaciones, String titulo, Date fechaEnvio, Date fechaProgramacion,
			EstadoNotificacion estado, Usuario usuario, String notificacion) {
		this.idNotificaciones = idNotificaciones;
		this.titulo = titulo;
		this.fechaEnvio = fechaEnvio;
		this.fechaProgramacion = fechaProgramacion;
		this.estado = estado;
		this.usuario = usuario;
		this.notificacion = notificacion;
	}

	public Notificacion() {
	}

	public Long getIdNotificaciones() {
		return idNotificaciones;
	}

	public void setIdNotificaciones(Long idNotificaciones) {
		this.idNotificaciones = idNotificaciones;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	public EstadoNotificacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoNotificacion estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}

	public List<Contacto> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<Contacto> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	@Override
	public String toString() {
		return "Notificacion [idNotificaciones=" + idNotificaciones + ", titulo=" + titulo + ", fechaEnvio="
				+ fechaEnvio + ", fechaProgramacion=" + fechaProgramacion + ", estado=" + estado + ", usuario="
				+ usuario + "]";
	}

	private static final long serialVersionUID = 1L;

}
