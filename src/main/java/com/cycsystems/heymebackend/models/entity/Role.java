package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="role")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRole;
	
	@NotEmpty
	@Column(name="descripcion", length=60, nullable=false)
	private String descripcion;
	
	@NotEmpty
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="estado")
	private Boolean estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEmpresa")
	private Empresa empresa;
	
	@OneToMany(mappedBy = "puesto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Permiso> permisos;

	public Role(Integer idRole) {
		this.idRole = idRole;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
