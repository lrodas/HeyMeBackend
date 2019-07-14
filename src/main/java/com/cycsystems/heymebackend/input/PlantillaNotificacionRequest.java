package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.PlantillaNotificacion;

public class PlantillaNotificacionRequest extends BaseInput {

	private PlantillaNotificacion plantilla;

	public PlantillaNotificacion getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(PlantillaNotificacion plantilla) {
		this.plantilla = plantilla;
	}

	@Override
	public String toString() {
		return "PlantillaNotificacionRequest [plantilla=" + plantilla + ", toString()=" + super.toString() + "]";
	}
}
