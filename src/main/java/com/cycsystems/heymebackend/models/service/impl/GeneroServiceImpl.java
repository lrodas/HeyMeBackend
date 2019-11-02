package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IGeneroDao;
import com.cycsystems.heymebackend.models.entity.Genero;
import com.cycsystems.heymebackend.models.service.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImpl implements IGeneroService {

	@Autowired
	private IGeneroDao generoRepository;
	
	@Override
	public Genero save(Genero entity) {
		return this.generoRepository.save(entity);
	}

	@Override
	public Genero findById(Integer id) {
		return this.generoRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.generoRepository.existsById(id);
	}

	@Override
	public List<Genero> findAll() {
		return this.generoRepository.findAll();
	}

	@Override
	public long count() {
		return this.generoRepository.count();
	}

}
