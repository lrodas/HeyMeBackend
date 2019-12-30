package com.cycsystems.heymebackend.common;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Consumo {
	private Integer idPaqueteConsumo;
	private Integer consumoEmail;
	private Integer consumoSMS;
	private Integer consumoWhatsapp;
	private Date fechaFin;
	private Date fechaInicio;
	private Empresa empresa;
	private Paquete paquete;
}
