package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao extends JpaRepository<Role, Integer> {

	public abstract List<Role> findByEstadoEquals(Boolean estado);
	
	public abstract List<Role> findByDescripcionEquals(String titulo);
	
	public abstract List<Role> findByDescripcionLikeIgnoreCase(String nombre);
}
