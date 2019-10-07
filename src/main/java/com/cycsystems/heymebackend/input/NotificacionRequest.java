package com.cycsystems.heymebackend.input;

import java.util.Date;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Notificacion;

public class NotificacionRequest extends BaseInput {

	private Notificacion notificacion;
	private String nombreUsuario;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer tipo;

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "NotificacionRequest [notificacion=" + notificacion + ", nombreUsuario=" + nombreUsuario
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", tipo=" + tipo + ", toString()="
				+ super.toString() + "]";
	}
}
