package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Usuario;

import java.util.Date;
import java.util.List;

public interface IUsuarioService {

	public Usuario save(Usuario entity);
	
	public Usuario findByUsername(String username);

	public Usuario findById(Integer id);

	public boolean existsById(Integer id);

	public List<Usuario> findAll();
	
	public abstract List<Usuario> findByStartDate(Date fechaInicio, Date fechaFin);
	
	public abstract List<Usuario> findByName(String nombres);

	public long count();
}
