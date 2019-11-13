package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParametroDao extends JpaRepository<Parametro, Integer> {

    public abstract Parametro findByEmpresa_IdEmpresaAndNombre(Integer idEmpresa, String nombre);
}
