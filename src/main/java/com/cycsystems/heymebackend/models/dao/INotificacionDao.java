package com.cycsystems.heymebackend.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Notificacion;

@Repository
public interface INotificacionDao extends JpaRepository<Notificacion, Long> {

	public abstract List<Notificacion> findByUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCaseAndEstado_idEstadoNotificacionEquals(String nombre, String apellido, Integer estado);
	
	public abstract List<Notificacion> findByTituloLikeIgnoreCaseAndEstado_idEstadoNotificacion(String titulo, Integer estado);
	
	public abstract List<Notificacion> findByFechaProgramacionBetween(Date fechaInicio, Date fechaFin);
	
	public abstract List<Notificacion> findByFechaEnvioLessThanAndEstado_idEstadoNotificacion(Date fecha, Integer idEstadoNotificacion);
	
	public abstract List<Notificacion> findByEstado_idEstadoNotificacionEquals(Integer estado);
}
