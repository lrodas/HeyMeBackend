package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlantillaNotificacionDao extends JpaRepository<PlantillaNotificacion, Integer> {

	public abstract List<PlantillaNotificacion> findByEmpresa_IdEmpresaAndTituloLikeIgnoreCase(Integer idEmpresa, String titulo);
	
	public abstract List<PlantillaNotificacion> findByEmpresa_IdEmpresaAndEstadoEquals(Integer idEmpresa, Boolean estado);

	public abstract List<PlantillaNotificacion> findByEmpresa_IdEmpresa(Integer idEmpresa);
	
}
