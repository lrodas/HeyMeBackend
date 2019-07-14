package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;

public interface IPlantillaNotificacionService {

	public PlantillaNotificacion save(PlantillaNotificacion entity);

	public PlantillaNotificacion findById(Integer id);

	public boolean existsById(Integer id);

	public List<PlantillaNotificacion> findAll();

	public long count();
	
	public abstract List<PlantillaNotificacion> findByTitle(String title);
	
	public abstract List<PlantillaNotificacion> findByEstado(Boolean estado);
}
