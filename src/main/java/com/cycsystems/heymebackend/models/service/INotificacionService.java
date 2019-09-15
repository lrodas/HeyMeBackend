package com.cycsystems.heymebackend.models.service;

import java.util.Date;
import java.util.List;

import com.cycsystems.heymebackend.models.entity.Notificacion;

public interface INotificacionService {

	public Notificacion save(Notificacion entity);

	public Notificacion findById(Long id);

	public boolean existsById(Long id);

	public List<Notificacion> findAll();

	public long count();
	
	public List<Notificacion> findByUser(String usuario, Integer estado);
	
	public abstract List<Notificacion> findByTitle(String title, Integer estado);
	
	public abstract List<Notificacion> findByDate(Date fechaInicio, Date fechaFin);
	
	public abstract List<Notificacion> findbySendingDate(Date fecha, Integer idEstadoNotificacion);
	
	public abstract List<Notificacion> findByStatus(Integer status);
}
