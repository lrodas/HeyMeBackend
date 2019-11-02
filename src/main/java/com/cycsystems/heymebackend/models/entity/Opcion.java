package com.cycsystems.heymebackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="opcion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Opcion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idOpcion;
	private String descripcion;
	private String orden;
	private String url;
	private boolean evento;
	private String icono;

	public Opcion(Integer idOpcion, String descripcion, String orden, String url, boolean evento, String icono) {
		this.idOpcion = idOpcion;
		this.descripcion = descripcion;
		this.orden = orden;
		this.url = url;
		this.evento = evento;
		this.icono = icono;
	}

	public Opcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public Opcion() {
	}

	public Integer getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEvento() {
		return evento;
	}

	public void setEvento(boolean evento) {
		this.evento = evento;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	@Override
	public String toString() {
		return "Opcion [idOpcion=" + idOpcion + ", descripcion=" + descripcion + ", orden=" + orden + ", url=" + url
				+ ", evento=" + evento + ", icono=" + icono + "]";
	}
}
