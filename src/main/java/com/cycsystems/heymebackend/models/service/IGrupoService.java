package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Grupo;

import java.util.List;

public interface IGrupoService {
    public abstract Grupo save(Grupo grupo);
    public abstract Grupo findById(Integer idGrupo);
    public abstract List<Grupo> findAll(Integer idEmpresa);
    public abstract List<Grupo> findByName(String name, Integer idEmpresa);
    public abstract Boolean existById(Integer idGrupo);
    public abstract Long count();
}