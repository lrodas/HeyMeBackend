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
		if (this.usuarioRepository.findById(id).isPresent()) {
			return this.usuarioRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public boolean existsById(Integer id) {
		return this.usuarioRepository.existsById(id);
	}

	@Override
	public List<Usuario> findAll(Integer idEmpresa) {
		return this.usuarioRepository.findByEmpresa_IdEmpresa(idEmpresa);
	}

	@Override
	public long count() {
		return this.usuarioRepository.count();
	}

	@Override
	public Usuario findByEnterpriseAndUsername(Integer idEmpresa, String username) {
		return this.usuarioRepository.findByEmpresa_IdEmpresaAndUsername(idEmpresa, username);
	}

	@Override
	public Usuario findByUsername(String username) {
		return this.usuarioRepository.findByUsername(username);
	}

	@Override
	public List<Usuario> findByStartDate(Integer idEmpresa, Date fechaInicio, Date fechaFin) {
		return this.usuarioRepository.findByEmpresa_IdEmpresaAndFechaAltaBetween(idEmpresa, fechaInicio, fechaFin);
	}

	@Override
	public List<Usuario> findByName(Integer idEmpresa, String nombres) {
		return this.usuarioRepository.findByEmpresa_IdEmpresaAndNombresLikeIgnoreCaseOrApellidosLikeIgnoreCase(idEmpresa, "%" + nombres + "%","%" + nombres + "%");
	}
}
