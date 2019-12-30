package com.cycsystems.heymebackend.models.entity;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notificacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotificaciones;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "fechaEnvio", nullable = false)
	private Date fechaEnvio;

	@Column(name = "fechaProgramacion")
	private Date fechaProgramacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoNotificacion", nullable=false)
	private EstadoNotificacion estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario", nullable=false)
	private Usuario usuario;

	@Column(name = "notificacion", nullable = false, columnDefinition = "TEXT")
	private String notificacion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idContacto", nullable=false)
	private List<Contacto> destinatarios;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCanal", nullable = false)
	private Canal canal;

	@Column(name = "codigo")
	private String codigo;

	@Column(name="estadoPago")
	private Boolean estadoPago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idEmpresa", nullable=false)
	private Empresa empresa;

	@PrePersist
	private void prePersist() {
		this.fechaProgramacion = new Date();
	}

	private static final long serialVersionUID = 1L;
}
