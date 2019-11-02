package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Region;

import java.util.List;

public interface IRegionService {

	public Region save(Region entity);

	public Region findById(Integer id);

	public boolean existsById(Integer id);

	public List<Region> findAll();

	public long count();
}
