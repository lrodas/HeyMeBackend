package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroDao extends JpaRepository<Genero, Integer>{

}
