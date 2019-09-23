package com.cycsystems.heymebackend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Role;

@Repository
public interface IRoleDao extends JpaRepository<Role, Integer> {

	public abstract List<Role> findByEstadoEquals(Boolean estado);
	
	public abstract List<Role> findByDescripcionEquals(String titulo);
	
	public abstract List<Role> findByDescripcionLikeIgnoreCase(String nombre);
}
