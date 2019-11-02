package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IOpcionDao;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.service.IOpcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcionServiceImpl implements IOpcionService {

	@Autowired
	private IOpcionDao opcionRepository;
	
	@Override
	public Opcion save(Opcion entity) {
		return this.opcionRepository.save(entity);
	}

	@Override
	public Opcion findById(Integer id) {
		return this.opcionRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.opcionRepository.existsById(id);
	}

	@Override
	public List<Opcion> findAll() {
		return this.opcionRepository.findAll();
	}

	@Override
	public long count() {
		return this.opcionRepository.count();
	}

}
