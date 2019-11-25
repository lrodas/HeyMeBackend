package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IDetallePaqueteDao;
import com.cycsystems.heymebackend.models.entity.DetallePaquete;
import com.cycsystems.heymebackend.models.service.IDetallePaqueteService;

@Service
public class DetallePaqueteServiceImpl implements IDetallePaqueteService {

	@Autowired
	private IDetallePaqueteDao detallePaqueteRepository;
	
	@Override
	public List<DetallePaquete> findByPaquete(Integer idPaquete) {
		return this.detallePaqueteRepository.findByPaquete_IdPaquete(idPaquete);
	}

}
