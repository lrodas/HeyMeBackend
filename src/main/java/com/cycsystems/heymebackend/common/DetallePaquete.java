package com.cycsystems.heymebackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetallePaquete {

    private Integer idDetallePaquete;
    private Canal canal;
    private Paquete paquete;
    private Integer cuota;
}
