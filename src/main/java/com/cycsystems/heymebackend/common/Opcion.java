package com.cycsystems.heymebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Opcion {

	private Integer idOpcion;
	private String descripcion;
	private String orden;
	private String url;
	private boolean evento;
	private String icono;
	private boolean mostrar;
}
