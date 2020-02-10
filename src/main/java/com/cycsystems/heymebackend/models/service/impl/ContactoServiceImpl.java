package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IContactoDao;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.service.IContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
	public List<Contacto> findAll(Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresa(idEmpresa);
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<Contacto> findByName(Integer idEmpresa, String name) {
		return this.repository.findByEmpresa_IdEmpresaAndNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(idEmpresa, "%" + name + "%", "%" + name + "%");
	}

	@Override
	public List<Contacto> findByCreationDate(Integer idEmpresa, Date fechaInicio, Date fechaFin) {
		return this.repository.findByEmpresa_IdEmpresaAndFechaCreacionBetween(idEmpresa, fechaInicio, fechaFin);
	}

	@Override
	public List<Contacto> findByStatus(Integer idEmpresa, Boolean status) {
		return this.repository.findByEmpresa_IdEmpresaAndEstadoEquals(idEmpresa, status);
	}

}
