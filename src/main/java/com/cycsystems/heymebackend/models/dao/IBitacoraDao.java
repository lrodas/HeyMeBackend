package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Bitacora;

@Repository
public interface IBitacoraDao extends JpaRepository<Bitacora, Long> {

	public abstract List<Bitacora> findByFechaBetween(Date fechaInicio, Date fechaFin);
}
