package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IRoleDao;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleRepository;
	
	@Override
	public Role save(Role entity) {
		return this.roleRepository.save(entity);
	}

	@Override
	public Role findById(Integer id) {
		return this.roleRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.roleRepository.existsById(id);
	}

	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

	@Override
	public long count() {
		return this.roleRepository.count();
	}

	@Override
	public List<Role> findByStatus(Boolean status) {
		return this.roleRepository.findByEstadoEquals(status);
	}

	@Override
	public List<Role> findByTitle(String title) {
		return this.roleRepository.findByDescripcionEquals(title);
	}

}
