package com.cycsystems.heymebackend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Permiso;

@Repository
public interface IPermisoDao extends JpaRepository<Permiso, Integer> {

	public abstract List<Permiso> findByPuesto_DescripcionLikeIgnoreCase(String descripcion);
	
	public abstract List<Permiso> findByPuesto_IdRole(Integer idRole);
}
