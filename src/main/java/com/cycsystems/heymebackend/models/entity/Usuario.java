package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario",uniqueConstraints= {@UniqueConstraint(columnNames= {"username"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	private String nombres;
	
	private String apellidos;
	
	private String direccion;
	
	private String telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idRole")
	private Role role;

	@Column(length = 150, unique = true)
	private String username;

	@Column(length = 60)
	private String password;
	
	private String img;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Genero genero;
	
	private Boolean enabled;
	
	private Date fechaAlta;

	private String accessKeyId;
	
	private String secretAccessKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoUsuario")
	private EstadoUsuario estadoUsuario;

	@PrePersist
	private void Prepersist() {
		this.fechaAlta = new Date();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
