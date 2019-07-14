package com.cycsystems.heymebackend.models.service;

import java.util.Date;
import java.util.List;

import com.cycsystems.heymebackend.models.entity.Contacto;

public interface IContactoService {

	public Contacto save(Contacto entity);

	public Contacto findById(Integer id);

	public boolean existsById(Integer id);

	public List<Contacto> findAll();

	public long count();
	
	public List<Contacto> findByName(String name);
	
	public List<Contacto> findByCreationDate(Date fechaInicio, Date fechaFin);
	
	public List<Contacto> findByStatus(Boolean status);
}
