package com.cycsystems.heymebackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="empresa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Empresa implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String direccion;
    private String telefono;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
