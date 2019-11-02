package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.DatosNotificacionPrecio;
import lombok.Data;

import java.util.List;

@Data
public class DatosNotificacionPrecioResponse extends BaseOutput {

    private List<DatosNotificacionPrecio> datos;
}
