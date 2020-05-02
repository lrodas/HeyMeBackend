package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGrupoDao extends JpaRepository<Grupo, Integer> {

    public abstract List<Grupo> findByEmpresa_IdEmpresaAndNombreLikeIgnoreCase(Integer idEmpresa, String nombre);
    public abstract List<Grupo> findByEmpresa_IdEmpresa(Integer IdEmpresa);
}
