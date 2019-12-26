package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaisDao extends JpaRepository<Pais, Integer> {

    public List<Pais> findByNombreLikeIgnoreCaseAndEstado(String nombre, Boolean estado);
    public List<Pais> findByCodigoLikeIgnoreCaseAndEstado(String codigo, Boolean estado);
    public List<Pais> findByEstado(Boolean estado);
}
