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
public class Empresa {
	private Integer idEmpresa;
    private String nombreEmpresa;
    private String direccion;
    private String telefono;
    private String codigo;
    private String logo;
}
