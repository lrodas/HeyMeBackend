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
    public List<PaqueteConsumo> findPackagesByStatusAndEndDate(Integer idEmpresa, Integer status, Date endDate) {
        return this.paqueteConsumoRepository.findByEmpresa_IdEmpresaAndEstado_IdEstadoPaqueteConsumoAndFechaFinLessThanEqual(idEmpresa, status, endDate);
    }

	@Override
	public PaqueteConsumo save(PaqueteConsumo entity) {
		return this.paqueteConsumoRepository.save(entity);
	}
}
