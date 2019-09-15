package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cycsystems.heymebackend.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{

	public Usuario findByUsername(String username);
	
	public List<Usuario> findByFechaAltaBetween(Date fechaInicio, Date fechaFin);
	
	public List<Usuario> findByNombresLikeIgnoreCaseOrApellidosLikeIgnoreCase(String nombres, String apellidos);
}
