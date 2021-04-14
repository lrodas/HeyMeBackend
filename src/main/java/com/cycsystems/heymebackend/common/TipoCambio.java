package com.cycsystems.heymebackend.common;

import java.util.Date;

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
public class TipoCambio {

	private Integer idTipoCambio;
	private Double valor;
	private Date fecha;
	private Boolean estado;

}
