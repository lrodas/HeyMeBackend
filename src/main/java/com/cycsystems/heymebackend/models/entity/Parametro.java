package com.cycsystems.heymebackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parametro")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Parametro implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParametro;

    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;
    private String nombre;
    private String valor;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
