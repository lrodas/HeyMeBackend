package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;

import java.util.Date;
import java.util.List;

public interface IPaqueteConsumoService {

	public List<PaqueteConsumo> findPackagesByCompanyAndStatusAndEndDate(Integer idEmpresa, Integer status, Date endDate);
	public PaqueteConsumo save (PaqueteConsumo entity);
	public List<PaqueteConsumo> findPackagesByCompanyStartDateAndStatus(Date fechaInicio, Integer status);
	public List<PaqueteConsumo> findPackageByStatusAndEndDate(Integer status, Date fechaFin);
	public List<PaqueteConsumo> findPackageByStatusAndStartDate(Integer status, Date fechaInicio);
}
