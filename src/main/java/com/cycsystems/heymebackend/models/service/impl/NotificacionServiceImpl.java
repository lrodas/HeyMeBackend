package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.INotificacionDao;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
	public List<Notificacion> findByProgrammingDate(Date fechaInicio, Date fechaFin) {
		return this.repository.findByFechaProgramacionBetween(fechaInicio, fechaFin);
	}

	@Override
	public List<Notificacion> findbySendingDate(Date fecha, Integer idEstadoNotificacion) {
		return this.repository.findByFechaEnvioLessThanEqualAndEstado_idEstadoNotificacion(fecha, idEstadoNotificacion);
	}

	@Override
	public List<Notificacion> findByStatus(Integer status) {
		return this.repository.findByEstado_idEstadoNotificacionEquals(status);
	}

	@Override
	public List<Notificacion> findByCompanyAndStatus(Integer idEmpresa, Integer idEstado) {
		return this.repository.findByUsuario_Empresa_IdEmpresaAndEstado_IdEstadoNotificacion(idEmpresa, idEstado);
	}

	@Override
	public List<Notificacion> findByCompanyAndStatusPayment(Integer idEmpresa, Boolean estado, Integer idEstado) {
		return this.repository.findByUsuario_Empresa_IdEmpresaAndEstadoPagoAndEstado_IdEstadoNotificacion(idEmpresa, estado, idEstado);
	}

	@Override
	public List<Notificacion> findByUser(String usuario) {
		return this.repository.findByUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCase("%" + usuario + "%", "%" + usuario + "%");
	}

	@Override
	public List<Notificacion> findByShippingDate(Date fechaInicio, Date fechaFin) {
		return this.repository.findByFechaEnvioBetween(fechaInicio, fechaFin);
	}

}
