package com.cycsystems.heymebackend.common;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notificacion {

	private Long idNotificaciones;
	private String codigo;
	private String titulo;
	private Date fechaEnvio;
	private Date fechaProgramacion;
	private EstadoNotificacion estado;
	private String notificacion;
	private List<Contacto> destinatarios;
	private Usuario usuario;
	private Canal canal;
	private Boolean estadoPago;
	private Empresa empresa;
}
