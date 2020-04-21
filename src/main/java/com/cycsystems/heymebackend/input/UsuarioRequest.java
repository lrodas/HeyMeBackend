package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Usuario;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioRequest extends BaseInput {

	private Usuario datos;
	private Date fechaInicio;
	private Date fechaFin;
	private String recaptchaResponse;
}
