package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="bitacora")
public class Bitacora implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBitacora;
	
	private Date fecha;
	
	@Column(name="pagina", nullable=false)
	private String pagina;
	
	@Column(name="metodo", nullable=false)
	private String metodo;
	
	@Column(name="json", nullable=false, columnDefinition="TEXT")
	private String json;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTipoOperacion", nullable=false)
	private TipoOperacion tipoOperacion;
	
	@Column(name="error", nullable=false)
	private String error;
	
	@PrePersist
	public void prePersist() {
		this.fecha = new Date();
	}
	
	public Bitacora(Long idBitacora, Date fecha, String pagina, String metodo, String json) {
		this.idBitacora = idBitacora;
		this.fecha = fecha;
		this.pagina = pagina;
		this.metodo = metodo;
		this.json = json;
	}

	public Bitacora() {
	}

	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Bitacora [idBitacora=" + idBitacora + ", fecha=" + fecha + ", pagina=" + pagina + ", metodo=" + metodo
				+ ", json=" + json + ", tipoOperacion=" + tipoOperacion + ", error=" + error + "]";
	}

	private static final long serialVersionUID = 1L;

}

