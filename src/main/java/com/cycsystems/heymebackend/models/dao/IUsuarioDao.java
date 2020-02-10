package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{

	public Usuario findByEmpresa_IdEmpresaAndUsername(Integer idEmpresa, String username);
	
	public List<Usuario> findByEmpresa_IdEmpresaAndFechaAltaBetween(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public List<Usuario> findByEmpresa_IdEmpresaAndNombresLikeIgnoreCaseOrApellidosLikeIgnoreCase(Integer idEmpresa, String nombres, String apellidos);

	public List<Usuario> findByEmpresa_IdEmpresa(Integer idEmpresa);

	public Usuario findByUsername(String username);
}
