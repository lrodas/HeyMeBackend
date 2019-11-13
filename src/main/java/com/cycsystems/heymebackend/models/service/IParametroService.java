package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Parametro;

public interface IParametroService {

    public abstract Parametro findParameterByEmpresaAndName(Integer idEmpresa, String nombre);

}
