package com.cycsystems.heymebackend.common;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
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
	private EstadoPaqueteConsumo estado;
}
