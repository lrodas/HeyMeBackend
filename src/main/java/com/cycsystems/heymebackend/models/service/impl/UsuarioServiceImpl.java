package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IUsuarioDao;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioDao usuarioRepository;
	
	@Override
	public Usuario save(Usuario entity) {
		return this.usuarioRepository.save(entity);
	}

	@Override
	public Usuario findById(Integer id) {
		return this.usuarioRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return this.usuarioRepository.existsById(id);
	}

	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) this.usuarioRepository.findAll();
	}

	@Override
	public long count() {
		return this.usuarioRepository.count();
	}

	@Override
	public Usuario findByUsername(String username) {
		return this.usuarioRepository.findByUsername(username);
	}

	@Override
	public List<Usuario> findByStartDate(Date fechaInicio, Date fechaFin) {
		return this.usuarioRepository.findByFechaAltaBetween(fechaInicio, fechaFin);
	}

	@Override
	public List<Usuario> findByName(String nombres) {
		return this.usuarioRepository.findByNombresLikeIgnoreCaseOrApellidosLikeIgnoreCase("%" + nombres + "%","%" + nombres + "%");
	}

}
