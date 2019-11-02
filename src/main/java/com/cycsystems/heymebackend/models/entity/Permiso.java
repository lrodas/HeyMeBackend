package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="permiso")
public class Permiso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPermiso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Opcion opcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Role puesto;
	
	private boolean alta;
	private boolean baja;
	private boolean cambio;
	private boolean imprimir;
	
	public Permiso(Integer idPermiso, Opcion opcion, Role puesto, boolean alta, boolean baja, boolean cambio,
			boolean imprimir) {
		this.idPermiso = idPermiso;
		this.opcion = opcion;
		this.puesto = puesto;
		this.alta = alta;
		this.baja = baja;
		this.cambio = cambio;
		this.imprimir = imprimir;
	}

	public Permiso() {
	}

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

	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public boolean isCambio() {
		return cambio;
	}

	public void setCambio(boolean cambio) {
		this.cambio = cambio;
	}

	public boolean isImprimir() {
		return imprimir;
	}

	public void setImprimir(boolean imprimir) {
		this.imprimir = imprimir;
	}

	@Override
	public String toString() {
		return "Permiso [idPermiso=" + idPermiso + ", opcion=" + opcion + ", alta=" + alta
				+ ", baja=" + baja + ", cambio=" + cambio + ", imprimir=" + imprimir + "]";
	}
}
