package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Genero;

public interface IGeneroService {

	public Genero save(Genero entity);

	public Genero findById(Integer id);

	public boolean existsById(Integer id);

	public List<Genero> findAll();

	public long count();
	
}
