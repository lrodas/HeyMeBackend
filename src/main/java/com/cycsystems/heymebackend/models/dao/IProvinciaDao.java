package com.cycsystems.heymebackend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Provincia;

@Repository
public interface IProvinciaDao extends JpaRepository<Provincia, Integer> {

	public abstract List<Provincia> findByRegion_IdRegion(Integer idRegion);
}
