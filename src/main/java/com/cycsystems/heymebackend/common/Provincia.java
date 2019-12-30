package com.cycsystems.heymebackend.common;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Provincia {

	private Integer idProvincia;
	private String nombre;
	private Region region;
}
