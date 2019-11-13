package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Notificacion;

import java.util.Date;
import java.util.List;

public interface INotificacionService {

	public Notificacion save(Notificacion entity);

	public Notificacion findById(Long id);

	public boolean existsById(Long id);

	public List<Notificacion> findAll();

	public long count();
	
	public abstract List<Notificacion> findByUser(Integer idEmpresa, String usuario, Integer estado);
	
	public abstract List<Notificacion> findByUser(Integer idEmpresa, String usuario);
	
	public abstract List<Notificacion> findByTitle(String title, Integer estado, Integer idEmpresa);
	
	public abstract List<Notificacion> findByProgrammingDate(Date fechaInicio, Date fechaFin, Integer idEmpresa);
	
	public abstract List<Notificacion> findByShippingDate(Date fechaInicio, Date fechaFin, Integer idEmpresa);
	
	public abstract List<Notificacion> findbySendingDate(Date fecha, Integer idEstadoNotificacion, Integer idEmpresa);
	
	public abstract List<Notificacion> findByStatus(Integer status, Integer idEmpresa);

	public abstract List<Notificacion> findByCompanyAndStatus(Integer idEmpresa, Integer idEstado);

	public abstract List<Notificacion> findByCompanyAndStatusPayment(Integer idEmpresa, Boolean estado, Integer idEstado);
}
