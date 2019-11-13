package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Role;

import java.util.List;

public interface IRoleService {
	public Role save(Role entity);

	public Role findById(Integer id);

	public boolean existsById(Integer id);

	public List<Role> findAll();
	
	public List<Role> findAll(Empresa empresa);

	public long count();
	
	public List<Role> findByStatus(Integer idEmpresa, Boolean status);
	
	public List<Role> findByTitle(Integer idEmpresa, String title);
	
	public List<Role> findByNombreLike(Integer idEmpresa, String nombre);
}
