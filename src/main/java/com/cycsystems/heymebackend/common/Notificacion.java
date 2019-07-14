package com.cycsystems.heymebackend.common;

import java.util.Date;

public class Notificacion {

	private Long idNotificaciones;
	private String titulo;
	private Date fechaEnvio;
	private Date fechaProgramacion;
	private EstadoNotificacion estado;
	private String notificacion;
	private Contacto destinatario;
	private Usuario usuario;
	private Canal canal;

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

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}

	public Contacto getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Contacto destinatario) {
		this.destinatario = destinatario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	@Override
	public String toString() {
		return "NotificacionRequest [idNotificaciones=" + idNotificaciones + ", titulo=" + titulo + ", fechaEnvio="
				+ fechaEnvio + ", fechaProgramacion=" + fechaProgramacion + ", estado=" + estado + "]";
	}
}
