package com.cycsystems.heymebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Paquete {
	private Integer idPaquete;
	private String nombre;
	private Double precioGTQ;
	private Double precioUSD;
	private String descripcion;
	private EstadoPaquete estadoPaquete;
	private List<DetallePaquete> detalle;
	private String icono;
}
