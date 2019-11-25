package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;

import java.util.Date;
import java.util.List;

public interface IPaqueteConsumoService {

	public List<PaqueteConsumo> findPackagesByStatusAndEndDate(Integer idEmpresa, Integer status, Date endDate);

	public PaqueteConsumo save (PaqueteConsumo entity);
	
}
