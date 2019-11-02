package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Opcion;

import java.util.List;

public interface IOpcionService {

	public Opcion save(Opcion entity);

	public Opcion findById(Integer id);

	public boolean existsById(Integer id);

	public List<Opcion> findAll();

	public long count();
}
