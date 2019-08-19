package com.cycsystems.heymebackend.output;

import java.util.ArrayList;
import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.PlantillaNotificacion;

public class PlantillaNotificacionResponse extends BaseOutput {

	private List<PlantillaNotificacion> plantillas;
	private PlantillaNotificacion plantilla;

	public PlantillaNotificacion getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(PlantillaNotificacion plantilla) {
		this.plantilla = plantilla;
	}

	public List<PlantillaNotificacion> getPlantillas() {
		if (this.plantillas == null) {
			this.plantillas = new ArrayList<>();
		}
		return plantillas;
	}

	public void setPlantillas(List<PlantillaNotificacion> plantillas) {
		this.plantillas = plantillas;
	}

	@Override
	public String toString() {
		return "PlantillaNotificacionResponse [plantillas=" + plantillas + ", plantilla=" + plantilla + "]";
	}
}
