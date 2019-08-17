package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IProvinciaDao;
import com.cycsystems.heymebackend.models.entity.Provincia;
import com.cycsystems.heymebackend.models.service.IProvinciaService;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	private IProvinciaDao repository;
	
	@Override
	public Provincia save(Provincia entity) {
		return this.repository.save(entity);
	}

	@Override
	public Provincia findById(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.repository.existsById(id);
	}

	@Override
	public List<Provincia> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<Provincia> findByRegion(Integer idRegion) {
		return this.repository.findByRegion_IdRegion(idRegion);
	}

}
