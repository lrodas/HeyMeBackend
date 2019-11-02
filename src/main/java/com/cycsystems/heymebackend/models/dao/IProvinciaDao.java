package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProvinciaDao extends JpaRepository<Provincia, Integer> {

	public abstract List<Provincia> findByRegion_IdRegion(Integer idRegion);
}
