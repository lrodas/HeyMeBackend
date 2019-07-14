package com.cycsystems.heymebackend.output;

import java.util.ArrayList;
import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Contacto;

public class ContactoResponse extends BaseOutput {

	private List<Contacto> contactos;

	public List<Contacto> getContactos() {
		if (this.contactos == null) {
			this.contactos = new ArrayList<>();
		}
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	@Override
	public String toString() {
		return "ContactoResponse [contactos=" + contactos + ", toString()=" + super.toString() + "]";
	}
}
