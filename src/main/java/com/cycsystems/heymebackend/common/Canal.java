package com.cycsystems.heymebackend.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Canal {
	private Integer idCanal;
	private String nombre;
	private Boolean estado;
	private Boolean mostrarPlantilla;
}
