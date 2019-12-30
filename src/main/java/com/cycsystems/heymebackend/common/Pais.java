package com.cycsystems.heymebackend.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pais {

	private Integer idPais;
	private String nombre;
	private String codigo;
	private Boolean estado;
}
