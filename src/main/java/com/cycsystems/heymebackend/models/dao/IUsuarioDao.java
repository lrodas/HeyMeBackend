package com.cycsystems.heymebackend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.cycsystems.heymebackend.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{

	public Usuario findByUsername(String username);
}
