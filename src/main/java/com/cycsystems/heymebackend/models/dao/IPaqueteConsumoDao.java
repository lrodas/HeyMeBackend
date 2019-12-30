package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;

@Repository
public interface IPaqueteConsumoDao extends JpaRepository<PaqueteConsumo, Integer> {

	public List<PaqueteConsumo> findByEmpresa_IdEmpresaAndEstado_IdEstadoPaqueteConsumo(Integer idEmpresa, Integer idEstadoPaquete);

	public List<PaqueteConsumo> findByEstado_IdEstadoPaqueteConsumoAndFechaInicio(Integer idEstadoPaquete, Date fechaInicio);

	public List<PaqueteConsumo> findByEstado_IdEstadoPaqueteConsumoAndFechaFinLessThan(Integer idEstadoPaquete, Date fechaFin);
}
