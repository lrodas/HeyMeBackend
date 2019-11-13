package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.DatosNotificacionPrecio;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DatosNotificacionPrecioResponse extends BaseOutput {

    private List<DatosNotificacionPrecio> datos;
}
