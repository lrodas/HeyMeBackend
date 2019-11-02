package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Provincia;

import java.util.List;

public class ProvinciaResponse extends BaseOutput {

	private List<Provincia> provincias;

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	@Override
	public String toString() {
		return "ProvinciaResponse [provincias=" + provincias + ", toString()=" + super.toString() + "]";
	}
}
