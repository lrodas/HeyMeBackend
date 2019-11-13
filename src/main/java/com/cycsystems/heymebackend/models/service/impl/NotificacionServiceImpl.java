package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.INotificacionDao;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificacionServiceImpl implements INotificacionService {

	private Logger LOG = LogManager.getLogger(NotificacionServiceImpl.class);
	
	@Autowired
	private INotificacionDao repository;
	
	@Override
	public Notificacion save(Notificacion entity) {
		Notificacion response = this.repository.save(entity); 
		return response;
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
	public List<Notificacion> findByUser(Integer idEmpresa, String usuario, Integer estado) {
		return this.repository.findByEmpresa_IdEmpresaAndUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCaseAndEstado_idEstadoNotificacionEquals(idEmpresa, "%" + usuario + "%", "%" + usuario + "%", estado);
	}

	@Override
	public List<Notificacion> findByTitle(String title, Integer estado, Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresaAndTituloLikeIgnoreCaseAndEstado_idEstadoNotificacion(idEmpresa, "%" + title + "%", estado);
	}

	@Override
	public List<Notificacion> findByProgrammingDate(Date fechaInicio, Date fechaFin, Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresaAndFechaProgramacionBetween(idEmpresa, fechaInicio, fechaFin);
	}

	@Override
	public List<Notificacion> findbySendingDate(Date fecha, Integer idEstadoNotificacion, Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresaAndFechaEnvioLessThanEqualAndEstado_idEstadoNotificacion(idEmpresa, fecha, idEstadoNotificacion);
	}

	@Override
	public List<Notificacion> findByStatus(Integer status, Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresaAndEstado_idEstadoNotificacionEquals(idEmpresa, status);
	}

	@Override
	public List<Notificacion> findByCompanyAndStatus(Integer idEmpresa, Integer idEstado) {
		return this.repository.findByEmpresa_IdEmpresaAndEstado_IdEstadoNotificacion(idEmpresa, idEstado);
	}

	@Override
	public List<Notificacion> findByCompanyAndStatusPayment(Integer idEmpresa, Boolean estado, Integer idEstado) {
		
		LOG.info("METHOD: findByCompanyAndStatusPayment() --PARAMS: idEmpresa: " + idEmpresa + ", estado: " + estado + ", idEstado: " + idEstado);
		return this.repository.findByEmpresa_IdEmpresaAndEstadoPagoAndEstado_IdEstadoNotificacion(idEmpresa, estado, idEstado);
	}

	@Override
	public List<Notificacion> findByUser(Integer idEmpresa, String usuario) {
		return this.repository.findByEmpresa_IdEmpresaAndUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCase(idEmpresa, "%" + usuario + "%", "%" + usuario + "%");
	}

	@Override
	public List<Notificacion> findByShippingDate(Date fechaInicio, Date fechaFin, Integer idEmpresa) {
		return this.repository.findByEmpresa_IdEmpresaAndFechaEnvioBetween(idEmpresa, fechaInicio, fechaFin);
	}

}
