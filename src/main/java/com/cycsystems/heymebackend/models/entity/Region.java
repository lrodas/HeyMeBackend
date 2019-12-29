package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="region")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Region implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRegion;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPais")
	private Pais pais;

	private static final long serialVersionUID = 1L;

}
