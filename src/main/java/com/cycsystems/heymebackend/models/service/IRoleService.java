package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Role;

public interface IRoleService {
	public Role save(Role entity);

	public Role findById(Integer id);

	public boolean existsById(Integer id);

	public List<Role> findAll();

	public long count();
	
	public List<Role> findByStatus(Boolean status);
	
	public List<Role> findByTitle(String title);
	
	public List<Role> findByNombreLike(String nombre);
}
