package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Contacto;

@Repository
public interface IContactoDao extends JpaRepository<Contacto, Integer> {

	public abstract List<Contacto> findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(String nombre, String apellido);
	
	public abstract List<Contacto> findByFechaCreacionBetween(Date fechaInicio, Date fechaFin);
	
	public abstract List<Contacto> findByEstadoEquals(Boolean estado);
}
