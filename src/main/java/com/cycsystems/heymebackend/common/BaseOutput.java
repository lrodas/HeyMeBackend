package com.cycsystems.heymebackend.common;

public class BaseOutput {

	private String indicador;
	private String codigo;
	private String descripcion;

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "BaseInput [indicador=" + indicador + ", codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}
}
