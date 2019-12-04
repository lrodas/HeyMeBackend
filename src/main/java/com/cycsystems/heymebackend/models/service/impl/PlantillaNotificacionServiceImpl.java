package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IPlantillaNotificacionDao;
import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;
import com.cycsystems.heymebackend.models.service.IPlantillaNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<PlantillaNotificacion> findAll(Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresa(idEmpresa);
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<PlantillaNotificacion> findByTitle(Integer idEmpresa, String title) {
		return this.repository.findByEmpresa_IdEmpresaAndTituloLikeIgnoreCase(idEmpresa, "%" + title + "%");
	}

	@Override
	public List<PlantillaNotificacion> findByEstado(Integer idEmpresa, Boolean estado) {
		return this.repository.findByEmpresa_IdEmpresaAndEstadoEquals(idEmpresa, estado);
	}

}
