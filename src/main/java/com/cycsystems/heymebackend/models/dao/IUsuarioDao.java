package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{

	public Usuario findByUsername(String username);
	
	public List<Usuario> findByFechaAltaBetween(Date fechaInicio, Date fechaFin);
	
	public List<Usuario> findByNombresLikeIgnoreCaseOrApellidosLikeIgnoreCase(String nombres, String apellidos);
}
