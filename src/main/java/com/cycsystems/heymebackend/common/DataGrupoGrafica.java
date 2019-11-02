package com.cycsystems.heymebackend.common;

import lombok.Data;

import java.util.List;

@Data
public class DataGrupoGrafica {

    private String name;
    private List<GraficaBarras> series;
}
