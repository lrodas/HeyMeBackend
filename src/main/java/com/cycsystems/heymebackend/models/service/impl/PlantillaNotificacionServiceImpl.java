package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IPlantillaNotificacionDao;
import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;
import com.cycsystems.heymebackend.models.service.IPlantillaNotificacionService;

@Service
public class PlantillaNotificacionServiceImpl implements IPlantillaNotificacionService {

	@Autowired
	private IPlantillaNotificacionDao repository;
	
	@Override
	public PlantillaNotificacion save(PlantillaNotificacion entity) {
		return this.repository.save(entity);
	}

	@Override
	public PlantillaNotificacion findById(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.repository.existsById(id);
	}

	@Override
	public List<PlantillaNotificacion> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<PlantillaNotificacion> findByTitle(String title) {
		return this.repository.findByTituloLikeIgnoreCase("%" + title + "%");
	}

	@Override
	public List<PlantillaNotificacion> findByEstado(Boolean estado) {
		return this.repository.findByEstadoEquals(estado);
	}

}
