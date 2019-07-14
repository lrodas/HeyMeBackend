package com.cycsystems.heymebackend.models.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.IContactoDao;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.service.IContactoService;

@Service
public class ContactoServiceImpl implements IContactoService {

	@Autowired
	private IContactoDao repository;
	
	@Override
	public Contacto save(Contacto entity) {
		return this.repository.save(entity);
	}

	@Override
	public Contacto findById(Integer id) {
		if (this.repository.findById(id).isPresent()) return this.repository.findById(id).get();
		else return null;		
	}

	@Override
	public boolean existsById(Integer id) {
		return this.repository.existsById(id);
	}

	@Override
	public List<Contacto> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<Contacto> findByName(String name) {
		return this.repository.findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase("%" + name + "%", "%" + name + "%");
	}

	@Override
	public List<Contacto> findByCreationDate(Date fechaInicio, Date fechaFin) {
		return this.repository.findByFechaCreacionBetween(fechaInicio, fechaFin);
	}

	@Override
	public List<Contacto> findByStatus(Boolean status) {
		return this.repository.findByEstadoEquals(status);
	}

}
