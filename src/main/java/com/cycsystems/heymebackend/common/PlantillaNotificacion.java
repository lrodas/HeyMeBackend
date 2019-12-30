package com.cycsystems.heymebackend.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlantillaNotificacion {

	private Integer idPlantillaNotificacion;
	private String titulo;
	private String plantilla;
	private Boolean estado;
	private Canal canal;
}
