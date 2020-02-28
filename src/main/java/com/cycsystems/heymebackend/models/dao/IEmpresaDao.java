package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpresaDao extends JpaRepository<Empresa, Integer> {

    public abstract Boolean existsByCodigo(String codigo);
}
