package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOpcionDao extends JpaRepository<Opcion, Integer> {

}
