package com.cycsystems.heymebackend.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Contacto {

	private Integer idContacto;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private Provincia provincia;
	private String direccion;
	private Boolean estado;
	private Pais pais;
	private Empresa empresa;
	private Grupo grupo;
	private Usuario usuario;
}
