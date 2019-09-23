package com.cycsystems.heymebackend.output;

import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Opcion;

public class OpcionResponse extends BaseOutput {

	private List<Opcion> opciones;
	private Opcion opcion; 

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}
}
