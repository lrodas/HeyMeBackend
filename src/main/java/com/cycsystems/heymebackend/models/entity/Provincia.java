package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="provincia")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Provincia implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idProvincia;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idRegion")
	private Region region;

	private static final long serialVersionUID = 1L;

}
