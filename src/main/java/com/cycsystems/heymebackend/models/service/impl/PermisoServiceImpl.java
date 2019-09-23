package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IPermisoDao;
import com.cycsystems.heymebackend.models.entity.Permiso;
import com.cycsystems.heymebackend.models.service.IPermisoService;

@Service
public class PermisoServiceImpl implements IPermisoService {

	@Autowired
	private IPermisoDao permisoRepository;
	
	@Override
	public Permiso save(Permiso entity) {
		return this.permisoRepository.save(entity);
	}

	@Override
	public Permiso findById(Integer id) {
		return this.permisoRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.permisoRepository.existsById(id);
	}

	@Override
	public List<Permiso> findAll() {
		return this.permisoRepository.findAll();
	}

	@Override
	public long count() {
		return this.permisoRepository.count();
	}

	@Override
	public List<Permiso> saveAll(List<Permiso> entities) {
		return this.permisoRepository.saveAll(entities);
	}

	@Override
	public List<Permiso> findByRole(String role) {
		return this.permisoRepository.findByPuesto_DescripcionLikeIgnoreCase("%" + role + "%");
	}

	@Override
	public List<Permiso> findByRole(Integer role) {
		return this.permisoRepository.findByPuesto_IdRole(role);
	}

}
