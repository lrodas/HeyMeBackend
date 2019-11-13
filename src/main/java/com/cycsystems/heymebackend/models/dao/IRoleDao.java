package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao extends JpaRepository<Role, Integer> {

	public abstract List<Role> findByEmpresa_IdEmpresaAndEstadoEquals(Integer idEmpresa, Boolean estado);
	
	public abstract List<Role> findByEmpresa_IdEmpresaAndDescripcionEquals(Integer idEmpresa, String titulo);
	
	public abstract List<Role> findByEmpresa_IdEmpresaAndDescripcionLikeIgnoreCase(Integer idEmpresa, String nombre);
	
	public abstract List<Role> findByEmpresa(Empresa empresa);
}
