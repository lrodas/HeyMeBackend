package com.cycsystems.heymebackend.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DatosNotificacionPrecio {
    private String canal;
    private BigDecimal precio;
}
