package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoResponse extends BaseOutput {

	private List<Contacto> contactos;
	private Contacto contacto;

	public List<Contacto> getContactos() {
		if (this.contactos == null) {
			this.contactos = new ArrayList<>();
		}
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	@Override
	public String toString() {
		return "ContactoResponse [contactos=" + contactos + ", contacto=" + contacto + "]";
	}
}
