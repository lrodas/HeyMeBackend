package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="estadoNotificacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EstadoNotificacion implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idEstadoNotificacion;
	
	@Column(nullable=false)
	private String descripcion;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
