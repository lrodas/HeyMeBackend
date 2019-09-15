package com.cycsystems.heymebackend.output;

import java.util.ArrayList;
import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Notificacion;

public class NotificacionResponse extends BaseOutput {

	private List<Notificacion> notificaciones;
	private Notificacion notificacion;

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	public List<Notificacion> getNotificaciones() {
		if (this.notificaciones == null) {
			this.notificaciones = new ArrayList<>();
		}
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	@Override
	public String toString() {
		return "NotificacionResponse [notificaciones=" + notificaciones + ", notificacion=" + notificacion + "]";
	}
}
