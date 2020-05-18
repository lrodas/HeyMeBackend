package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IContactoDao extends JpaRepository<Contacto, Integer> {

	public abstract List<Contacto> findByEmpresa_IdEmpresa(Integer idEmpresa);
	
	public abstract List<Contacto> findByEmpresa_IdEmpresaAndNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(Integer idEmpresa, String nombre, String apellido);
	
	public abstract List<Contacto> findByEmpresa_IdEmpresaAndFechaCreacionBetween(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public abstract List<Contacto> findByEmpresa_IdEmpresaAndEstadoEquals(Integer idEmpresa, Boolean estado);

	public abstract List<Contacto> findByEmpresa_IdEmpresaAndGrupo_NombreLikeIgnoreCase(Integer idEmpresa, String nombre);

	public abstract List<Contacto> findByEmpresa_IdEmpresaAndGrupo_IdGrupo(Integer idEmpresa, Integer idGrupo);
}
