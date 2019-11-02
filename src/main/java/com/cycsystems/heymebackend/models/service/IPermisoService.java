package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Permiso;

import java.util.List;

public interface IPermisoService {

	public List<Permiso> saveAll(List<Permiso> entities);
	
	public Permiso save(Permiso entity);

	public Permiso findById(Integer id);

	public boolean existsById(Integer id);

	public List<Permiso> findAll();

	public long count();
	
	public List<Permiso> findByRole(String role);
	
	public List<Permiso> findByRole(Integer role);
}
