package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="permiso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permiso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idPermiso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Opcion opcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Role puesto;
	
	private boolean alta;
	private boolean baja;
	private boolean cambio;
	private boolean imprimir;
}
