package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Empresa;

import java.util.List;

public interface IEmpresaService {

    public Empresa save(Empresa entity);

    public Empresa findById(Integer id);

    public boolean existsById(Integer id);

    public boolean existsByCode(String code);

    public List<Empresa> findAll();

    public long count();

    public List<Empresa> findByName(String name);

    public void removeEmpresa(Empresa empresa);

    public Empresa findByCode(String code);

}
