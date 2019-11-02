package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Contacto;

import java.util.Date;

public class ContactoRequest extends BaseInput {

	private Contacto contacto;
	private Date fechaInicio;
	private Date fechaFin;

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
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

	@Override
	public String toString() {
		return "ContactoRequest [contacto=" + contacto + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", toString()=" + super.toString() + "]";
	}
}
