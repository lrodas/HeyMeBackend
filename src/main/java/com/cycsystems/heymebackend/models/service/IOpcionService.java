package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Opcion;

public interface IOpcionService {

	public Opcion save(Opcion entity);

	public Opcion findById(Integer id);

	public boolean existsById(Integer id);

	public List<Opcion> findAll();

	public long count();
}
