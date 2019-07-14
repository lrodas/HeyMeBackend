package com.cycsystems.heymebackend.models.service;

import java.util.Date;
import java.util.List;

import com.cycsystems.heymebackend.models.entity.Bitacora;

public interface IBitacoraService {

	public Bitacora save(Bitacora entity);

	public Bitacora findById(Long id);

	public boolean existsById(Long id);

	public List<Bitacora> findAll();

	public long count();
	
	public List<Bitacora> findByDate(Date fechaInicio, Date fechaFin);
	
}
