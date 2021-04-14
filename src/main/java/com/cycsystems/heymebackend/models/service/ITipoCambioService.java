package com.cycsystems.heymebackend.models.service;

import java.util.Date;

import com.cycsystems.heymebackend.models.entity.TipoCambio;

public interface ITipoCambioService {

	public TipoCambio save(TipoCambio tipoCambio);

	public TipoCambio findById(Integer id);

	public TipoCambio findByFecha(Date fecha);

	public boolean existsById(Integer id);

	public boolean existsByFecha(Date fecha);

}
