package com.cycsystems.heymebackend.models.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IBitacoraDao;
import com.cycsystems.heymebackend.models.entity.Bitacora;
import com.cycsystems.heymebackend.models.service.IBitacoraService;

@Service
public class BitacoraServiceImpl implements IBitacoraService {

	@Autowired
	private IBitacoraDao bitacoraService;
	
	@Override
	public Bitacora save(Bitacora entity) {
		return this.bitacoraService.save(entity);
	}

	@Override
	public Bitacora findById(Long id) {
		return this.bitacoraService.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return this.existsById(id);
	}

	@Override
	public List<Bitacora> findAll() {
		return this.bitacoraService.findAll();
	}

	@Override
	public long count() {
		return this.bitacoraService.count();
	}

	@Override
	public List<Bitacora> findByDate(Date fechaInicio, Date fechaFin) {
		return this.bitacoraService.findByFechaBetween(fechaInicio, fechaFin);
	}

}
