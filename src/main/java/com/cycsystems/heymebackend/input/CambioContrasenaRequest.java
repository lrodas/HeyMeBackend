package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CambioContrasenaRequest extends BaseInput {

	private String contrasenaActual;
	private String nuevaContrasena;
}
