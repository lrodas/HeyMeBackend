package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaqueteDao extends JpaRepository<Paquete, Integer> {

    public List<Paquete> findByEstado_IdEstadoPaquete(Integer idEstadoPaquete);
}
