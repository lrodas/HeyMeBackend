package com.cycsystems.heymebackend.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
	private EstadoUsuario estadoUsuario;
	private Date fechaAlta;
}
