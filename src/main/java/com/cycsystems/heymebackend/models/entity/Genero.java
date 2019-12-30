package com.cycsystems.heymebackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="genero")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Genero implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idGenero;
	
	private String descripcion;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
