package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Region;

import java.util.List;

public class RegionResponse extends BaseOutput {

	private List<Region> regiones;

	public List<Region> getRegiones() {
		return regiones;
	}

	public void setRegiones(List<Region> regiones) {
		this.regiones = regiones;
	}

	@Override
	public String toString() {
		return "RegionResponse [regiones=" + regiones + ", toString()=" + super.toString() + "]";
	}
}
