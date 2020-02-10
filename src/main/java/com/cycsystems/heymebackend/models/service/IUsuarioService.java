package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Usuario;

import java.util.Date;
import java.util.List;

public interface IUsuarioService {

	public Usuario save(Usuario entity);
	
	public Usuario findByUsername(String username);

	public Usuario findByEnterpriseAndUsername(Integer idEmpresa, String username);

	public Usuario findById(Integer id);

	public boolean existsById(Integer id);

	public List<Usuario> findAll(Integer idEmmpresa);
	
	public abstract List<Usuario> findByStartDate(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public abstract List<Usuario> findByName(Integer idEmpresa, String nombres);

	public long count();
}
