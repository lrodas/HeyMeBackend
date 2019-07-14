package com.cycsystems.heymebackend.common;

public class Permiso {

	private Integer idPermiso;
	private Opcion opcion;
	private Role puesto;
	private Boolean alta;
	private Boolean baja;
	private Boolean cambio;
	private Boolean imprimir;

	public Integer getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public Role getPuesto() {
		return puesto;
	}

	public void setPuesto(Role puesto) {
		this.puesto = puesto;
	}

	public Boolean isAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public Boolean isBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	public Boolean isCambio() {
		return cambio;
	}

	public void setCambio(Boolean cambio) {
		this.cambio = cambio;
	}

	public Boolean isImprimir() {
		return imprimir;
	}

	public void setImprimir(Boolean imprimir) {
		this.imprimir = imprimir;
	}

	@Override
	public String toString() {
		return "Permiso [idPermiso=" + idPermiso + ", opcion=" + opcion + ", puesto=" + puesto + ", alta=" + alta
				+ ", baja=" + baja + ", cambio=" + cambio + ", imprimir=" + imprimir + "]";
	}
}
