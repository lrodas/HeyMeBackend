package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;

@Repository
public interface IPaqueteConsumoDao extends JpaRepository<PaqueteConsumo, Integer> {

	public List<PaqueteConsumo> findByEmpresa_IdEmpresaAndEstado_IdEstadoPaqueteConsumoAndFechaFinGreaterThanEqual(Integer idEmpresa, Integer idEstadoPaquete, Date fechaFin);

	public List<PaqueteConsumo> findByEstado_IdEstadoPaqueteConsumoAndFechaInicioEqual(Integer idEstadoPaquete, Date fechaInicio);

	public List<PaqueteConsumo> findByEstado_IdEstadoPaqueteConsumoAndFechaLessThanEqual(Integer idEstadoPaquete, Date fechaFin);
}
