package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IPaqueteConsumoDao;
import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;
import com.cycsystems.heymebackend.models.service.IPaqueteConsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaqueteConsumoServiceImpl implements IPaqueteConsumoService {

    @Autowired
    private IPaqueteConsumoDao paqueteConsumoRepository;

    @Override
    public List<PaqueteConsumo> findPackagesByCompanyAndStatus(Integer idEmpresa, Integer status) {
        return this.paqueteConsumoRepository.findByEmpresa_IdEmpresaAndEstado_IdEstadoPaqueteConsumo(idEmpresa, status);
    }

	@Override
	public PaqueteConsumo save(PaqueteConsumo entity) {
		return this.paqueteConsumoRepository.save(entity);
	}

    @Override
    public List<PaqueteConsumo> findPackagesByCompanyStartDateAndStatus(Date fechaInicio, Integer status) {
        return this.paqueteConsumoRepository.findByEstado_IdEstadoPaqueteConsumoAndFechaInicio(status, fechaInicio);
    }

    @Override
    public List<PaqueteConsumo> findPackageByStatusAndEndDate(Integer status, Date fechaFin) {
        return this.paqueteConsumoRepository.findByEstado_IdEstadoPaqueteConsumoAndFechaFinLessThan(status, fechaFin);
    }

    @Override
    public List<PaqueteConsumo> findPackageByStatusAndStartDate(Integer status, Date fechaInicio) {
        return this.paqueteConsumoRepository.findByEstado_IdEstadoPaqueteConsumoAndFechaInicio(status, fechaInicio);
    }
}
