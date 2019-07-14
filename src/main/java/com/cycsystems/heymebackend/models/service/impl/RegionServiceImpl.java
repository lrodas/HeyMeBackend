package com.cycsystems.heymebackend.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IRegionDao;
import com.cycsystems.heymebackend.models.entity.Region;
import com.cycsystems.heymebackend.models.service.IRegionService;

@Service
public class RegionServiceImpl implements IRegionService {

	@Autowired
	private IRegionDao repository;
	
	@Override
	public Region save(Region entity) {
		return this.repository.save(entity);
	}

	@Override
	public Region findById(Integer id) {
		if (this.repository.findById(id).isPresent()) return this.repository.findById(id).get();
		else return null;
	}

	@Override
	public boolean existsById(Integer id) {
		return this.repository.existsById(id);
	}

	@Override
	public List<Region> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

}
