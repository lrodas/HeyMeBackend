package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface INotificacionDao extends JpaRepository<Notificacion, Long> {

	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCaseAndEstado_idEstadoNotificacionEquals(Integer idEmpresa, String nombre, String apellido, Integer estado);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndTituloLikeIgnoreCaseAndEstado_idEstadoNotificacion(Integer idEmpresa, String titulo, Integer estado);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndFechaProgramacionBetween(Integer idEmpresa, Date fechaInicio, Date fechaFin);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndFechaEnvioLessThanEqualAndEstado_idEstadoNotificacion(Integer idEmpresa, Date fecha, Integer idEstadoNotificacion);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndEstado_idEstadoNotificacionEquals(Integer idEmpresa, Integer estado);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndUsuario_NombresLikeIgnoreCaseOrUsuario_ApellidosLikeIgnoreCase(Integer idEmpresa, String nombre, String apellido);
	
	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndFechaEnvioBetween(Integer idEmpresa, Date fechaInicio, Date fechaFin);

	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndEstado_IdEstadoNotificacion(Integer idEmpresa, Integer idEstado);

	public abstract List<Notificacion> findByEmpresa_IdEmpresaAndEstadoPagoAndEstado_IdEstadoNotificacion(Integer idEmpresa, Boolean estado, Integer idEstado);
}
