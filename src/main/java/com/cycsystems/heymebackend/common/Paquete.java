package com.cycsystems.heymebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Paquete {
	private Integer idPaquete;
	private String nombre;
	private Double precio;
}
