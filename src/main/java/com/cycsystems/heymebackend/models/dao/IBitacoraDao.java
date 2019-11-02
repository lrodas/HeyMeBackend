package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IBitacoraDao extends JpaRepository<Bitacora, Long> {

	public abstract List<Bitacora> findByFechaBetween(Date fechaInicio, Date fechaFin);
}
