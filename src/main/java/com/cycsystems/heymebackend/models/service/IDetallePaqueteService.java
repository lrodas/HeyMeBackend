package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.DetallePaquete;

public interface IDetallePaqueteService {

	public List<DetallePaquete> findByPaquete(Integer idPaquete);
}
