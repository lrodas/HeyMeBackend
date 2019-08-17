package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Provincia;

public interface IProvinciaService {

	public Provincia save(Provincia entity);

	public Provincia findById(Integer id);

	public boolean existsById(Integer id);

	public List<Provincia> findAll();

	public long count();
	
	public abstract List<Provincia> findByRegion(Integer idRegion);
}
