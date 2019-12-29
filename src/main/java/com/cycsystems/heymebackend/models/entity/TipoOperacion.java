package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipoOperacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TipoOperacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoOperacion;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	private static final long serialVersionUID = 1L;
}
