package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="contacto")
public class Contacto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idContacto;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="apellido", nullable=false)
	private String apellido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPais", nullable = false)
	private Pais pais;
	
	@Column(name="telefono", nullable=false)
	private String telefono;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idProvincia", nullable = true)
	private Provincia provincia;
	
	@Column(name="direccion", nullable=false)
	private String direccion;
	
	@Column(name="estado", columnDefinition="TINYINT DEFAULT 1")
	private Boolean estado;
	
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUsuario", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa", nullable = false)
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idGrupo")
	private Grupo grupo;
	
	@PrePersist
	private void prePersist() {
		this.fechaCreacion = new Date();
	}

	private static final long serialVersionUID = 1L;
}
