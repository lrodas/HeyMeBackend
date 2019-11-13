package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IParametroDao;
import com.cycsystems.heymebackend.models.entity.Parametro;
import com.cycsystems.heymebackend.models.service.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametroServiceImpl implements IParametroService {

    @Autowired
    private IParametroDao repository;

    @Override
    public Parametro findParameterByEmpresaAndName(Integer idEmpresa, String nombre) {
        return this.repository.findByEmpresa_IdEmpresaAndNombre(idEmpresa, nombre);
    }
}
