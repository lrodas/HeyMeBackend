package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Region;

public interface IRegionService {

	public Region save(Region entity);

	public Region findById(Integer id);

	public boolean existsById(Integer id);

	public List<Region> findAll();

	public long count();
}
