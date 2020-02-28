package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Parametro;

import java.util.List;

public interface IParametroService {

    public abstract Parametro findParameterByEmpresaAndName(Integer idEmpresa, String nombre);

    public abstract List<Parametro> save(List<Parametro> parametros);

}
