package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Paquete;

import java.util.List;

public interface IPaqueteService {

    public Paquete save(Paquete entity);
    public Paquete findById(Integer idPaquete);
    public List<Paquete> findAll();
    public List<Paquete> findByStatusPackage(Integer idPackage);
    public Long count();
}
