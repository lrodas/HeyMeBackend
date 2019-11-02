package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Bitacora;

import java.util.Date;
import java.util.List;

public interface IBitacoraService {

	public Bitacora save(Bitacora entity);

	public Bitacora findById(Long id);

	public boolean existsById(Long id);

	public List<Bitacora> findAll();

	public long count();
	
	public List<Bitacora> findByDate(Date fechaInicio, Date fechaFin);
	
}
