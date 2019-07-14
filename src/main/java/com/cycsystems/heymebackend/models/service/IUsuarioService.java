package com.cycsystems.heymebackend.models.service;

import java.util.List;

import com.cycsystems.heymebackend.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario save(Usuario entity);
	
	public Usuario findByUsername(String username);

	public Usuario findById(Integer id);

	public boolean existsById(Integer id);

	public List<Usuario> findAll();

	public long count();
}
