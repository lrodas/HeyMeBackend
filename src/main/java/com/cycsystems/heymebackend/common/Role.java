package com.cycsystems.heymebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {

	private Integer idRole;
	private String descripcion;
	private String nombre;
	private Boolean estado;
	private Empresa empresa;
	private List<Permiso> permisos;
}
