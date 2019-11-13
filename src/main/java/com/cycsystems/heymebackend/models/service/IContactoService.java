package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Contacto;

import java.util.Date;
import java.util.List;

public interface IContactoService {

	public Contacto save(Contacto entity);

	public Contacto findById(Integer id);

	public boolean existsById(Integer id);

	public List<Contacto> findAll(Integer idEmpresa);

	public long count();
	
	public List<Contacto> findByName(Integer idEmpresa, String name);
	
	public List<Contacto> findByCreationDate(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public List<Contacto> findByStatus(Integer idEmpresa, Boolean status);
}
