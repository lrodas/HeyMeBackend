package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IContactoDao extends JpaRepository<Contacto, Integer> {

	public abstract List<Contacto> findByUsuario_Empresa_IdEmpresa(Integer idEmpresa);
	
	public abstract List<Contacto> findByUsuario_Empresa_IdEmpresaAndNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(Integer idEmpresa, String nombre, String apellido);
	
	public abstract List<Contacto> findByUsuario_Empresa_IdEmpresaAndFechaCreacionBetween(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public abstract List<Contacto> findByUsuario_Empresa_IdEmpresaAndEstadoEquals(Integer idEmpresa, Boolean estado);
}
