package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.DataGrupoGrafica;
import com.cycsystems.heymebackend.common.GraficaBarras;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DatosGraficaResponse extends BaseOutput {
    private List<GraficaBarras> datos;
    private List<DataGrupoGrafica> series;
}
