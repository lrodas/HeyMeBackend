package com.cycsystems.heymebackend.models.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.ITipoCambioDao;
import com.cycsystems.heymebackend.models.entity.TipoCambio;
import com.cycsystems.heymebackend.models.service.ITipoCambioService;

@Service
public class TipoCambioServiceImpl implements ITipoCambioService {

	@Autowired
	private ITipoCambioDao repository;

	@Override
	public TipoCambio save(TipoCambio tipoCambio) {
		return this.repository.save(tipoCambio);
	}

	@Override
	public TipoCambio findById(Integer id) {
		if (this.repository.findById(id).isPresent())
			return this.repository.findById(id).get();
		else
			return null;
	}

	@Override
	public TipoCambio findByFecha(Date fecha) {
		return this.repository.findByFecha(fecha);
	}

	@Override
	public boolean existsById(Integer id) {
		return this.repository.existsById(id);
	}

	@Override
	public boolean existsByFecha(Date fecha) {
		return this.repository.existsByFecha(fecha);
	}

}
