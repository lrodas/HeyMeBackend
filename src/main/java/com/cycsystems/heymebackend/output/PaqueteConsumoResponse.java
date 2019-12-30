package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.NotificacionesRestantes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaqueteConsumoResponse extends BaseOutput {

	private NotificacionesRestantes paqueteActivo;
}
