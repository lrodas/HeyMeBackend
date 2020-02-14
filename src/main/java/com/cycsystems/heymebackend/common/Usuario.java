package com.cycsystems.heymebackend.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Usuario {

	private Integer idUsuario;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String telefono;
	private Role role;
	private String username;
	private String password;
	private String img;
	private Genero genero;
	private Boolean enabled;
	private Empresa empresa;
}
