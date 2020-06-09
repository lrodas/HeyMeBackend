package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Consumo;

import com.cycsystems.heymebackend.common.Paquete;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaqueteResponse extends BaseOutput {

	private Consumo consumo;
	private List<Paquete> paquetes;
}
