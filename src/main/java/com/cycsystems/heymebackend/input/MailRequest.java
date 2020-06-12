package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MailRequest extends BaseInput {

    private String remitente;
    private String destinatario;
    private String mensaje;
    private String asunto;
    private String codigoEmpresa;
}
