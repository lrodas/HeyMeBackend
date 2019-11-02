package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.DataGrupoGrafica;
import com.cycsystems.heymebackend.common.GraficaBarras;
import lombok.Data;

import java.util.List;

@Data
public class DatosGraficaResponse extends BaseOutput {
    private List<GraficaBarras> datos;
    private List<DataGrupoGrafica> series;
}
