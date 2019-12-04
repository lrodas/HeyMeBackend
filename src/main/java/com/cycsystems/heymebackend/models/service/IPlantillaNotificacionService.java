package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;

import java.util.List;

public interface IPlantillaNotificacionService {

	public PlantillaNotificacion save(PlantillaNotificacion entity);

	public PlantillaNotificacion findById(Integer id);

	public boolean existsById(Integer id);

	public List<PlantillaNotificacion> findAll(Integer idEmpresa);

	public long count();
	
	public abstract List<PlantillaNotificacion> findByTitle(Integer idEmpresa, String title);
	
	public abstract List<PlantillaNotificacion> findByEstado(Integer idEmpresa, Boolean estado);
}
