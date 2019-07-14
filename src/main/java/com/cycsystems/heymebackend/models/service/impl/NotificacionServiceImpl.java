package com.cycsystems.heymebackend.models.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycsystems.heymebackend.models.dao.INotificacionDao;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;

@Service
public class NotificacionServiceImpl implements INotificacionService {

	@Autowired
	private INotificacionDao repository;
	
	@Override
	public Notificacion save(Notificacion entity) {
		return this.repository.save(entity);
	}

	@Override
	public Notificacion findById(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return this.repository.existsById(id);
	}

	@Override
	public List<Notificacion> findAll() {
		return this.repository.findAll();
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public List<Notificacion> findByUser(String usuario, Integer estado) {
		return this.repository.findByUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCaseAndEstado_idEstadoNotificacionEquals("%" + usuario + "%", "%" + usuario + "%", estado);
	}

	@Override
	public List<Notificacion> findByTitle(String title, Integer estado) {
		return this.repository.findByTituloLikeIgnoreCaseAndEstado_idEstadoNotificacion("%" + title + "%", estado);
	}

	@Override
	public List<Notificacion> findByDate(Date fechaInicio, Date fechaFin, Integer estado) {
		return this.repository.findByFechaProgramacionBetweenAndEstado_idEstadoNotificacion(fechaInicio, fechaFin, estado);
	}

	@Override
	public List<Notificacion> findbySendingDate(Date fecha, Integer idEstadoNotificacion) {
		return this.repository.findByFechaEnvioLessThanAndEstado_idEstadoNotificacion(fecha, idEstadoNotificacion);
	}

}
