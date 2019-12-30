package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="canal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Canal implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCanal;
	private String nombre;

	private static final long serialVersionUID = 1L;

}
