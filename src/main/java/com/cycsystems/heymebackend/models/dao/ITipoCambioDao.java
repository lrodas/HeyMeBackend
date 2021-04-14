package com.cycsystems.heymebackend.models.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.TipoCambio;

@Repository
public interface ITipoCambioDao extends JpaRepository<TipoCambio, Integer> {

	public abstract TipoCambio findByFecha(Date fecha);

	public boolean existsByFecha(Date fecha);
}