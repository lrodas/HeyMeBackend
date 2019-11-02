package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Genero;

import java.util.List;

public interface IGeneroService {

	public Genero save(Genero entity);

	public Genero findById(Integer id);

	public boolean existsById(Integer id);

	public List<Genero> findAll();

	public long count();
	
}
