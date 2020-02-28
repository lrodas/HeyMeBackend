package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IPermisoDao;
import com.cycsystems.heymebackend.models.dao.IRoleDao;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Permiso;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleRepository;
	
	@Autowired
	private IPermisoDao permisoRepository; 
	
	@Override
	@Transactional
	public Role save(Role entity) {
		
		Role role = this.roleRepository.save(entity);
		List<Permiso> permisos = entity.getPermisos();
		if (permisos != null && permisos.size() > 0) {
			for (Permiso permiso: permisos) {
				permiso.setPuesto(role);
				this.permisoRepository.save(permiso);
			}
		}
		
		role = this.roleRepository.findById(role.getIdRole()).get();
		
		return role;
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
	public List<Role> findByStatus(Integer idEmpresa, Boolean status) {
		return this.roleRepository.findByEmpresa_IdEmpresaAndEstadoEquals(idEmpresa, status);
	}

	@Override
	public List<Role> findByTitle(Integer idEmpresa, String title) {
		return this.roleRepository.findByEmpresa_IdEmpresaAndDescripcionEquals(idEmpresa, title);
	}

	@Override
	public List<Role> findByNombreLike(Integer idEmpresa, String nombre) {
		return this.roleRepository.findByEmpresa_IdEmpresaAndDescripcionLikeIgnoreCase(idEmpresa, "%" + nombre + "%");
	}

	@Override
	public List<Role> findAll(Integer idEmpresa) {
		return this.roleRepository.findByEmpresa_IdEmpresa(idEmpresa);
	}

}
