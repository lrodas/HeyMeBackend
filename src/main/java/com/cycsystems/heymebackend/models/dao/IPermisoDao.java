package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermisoDao extends JpaRepository<Permiso, Integer> {

	public abstract List<Permiso> findByPuesto_DescripcionLikeIgnoreCase(String descripcion);
	
	public abstract List<Permiso> findByPuesto_IdRole(Integer idRole);
}
