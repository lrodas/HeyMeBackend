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
public class Grupo {

    private Integer idGrupo;
    private String nombre;
    private Empresa empresa;
}
