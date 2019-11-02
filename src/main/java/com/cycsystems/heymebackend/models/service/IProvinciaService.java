package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Provincia;

import java.util.List;

public interface IProvinciaService {

	public Provincia save(Provincia entity);

	public Provincia findById(Integer id);

	public boolean existsById(Integer id);

	public List<Provincia> findAll();

	public long count();
	
	public abstract List<Provincia> findByRegion(Integer idRegion);
}
