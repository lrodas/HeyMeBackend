package com.cycsystems.heymebackend.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificacionesRestantes {

	private Integer cantidadMensajes;
	private Integer cantidadWhatsapp;
	private Integer cantidadCorreo;
}
