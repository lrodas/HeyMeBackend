 package com.cycsystems.heymebackend.restcontrollers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.common.Canal;
import com.cycsystems.heymebackend.common.DataGrupoGrafica;
import com.cycsystems.heymebackend.common.DatosNotificacionPrecio;
import com.cycsystems.heymebackend.common.EstadoNotificacion;
import com.cycsystems.heymebackend.common.Genero;
import com.cycsystems.heymebackend.common.GraficaBarras;
import com.cycsystems.heymebackend.common.NotificacionesRestantes;
import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.common.Region;
import com.cycsystems.heymebackend.common.Role;
import com.cycsystems.heymebackend.input.NotificacionRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.DetallePaquete;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.IDetallePaqueteService;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.IPaqueteConsumoService;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.models.service.impl.SMSServiceImpl;
import com.cycsystems.heymebackend.output.DatosGraficaResponse;
import com.cycsystems.heymebackend.output.DatosNotificacionPrecioResponse;
import com.cycsystems.heymebackend.output.NotificacionResponse;
import com.cycsystems.heymebackend.output.PaqueteConsumoResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
import com.cycsystems.heymebackend.util.Util;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/notification")
public class NotificationController {
	
	private Logger LOG = LogManager.getLogger(NotificationController.class);
	
	@Autowired
	private INotificacionService notificacionService;
	@Autowired
	private IContactoService contactoService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IParametroService parametroService;
	@Autowired
	private IPaqueteConsumoService paqueteConsumoService;
	@Autowired
	private IDetallePaqueteService detallePaqueteService;
	@Autowired
	private SMSServiceImpl smsService;
	@Autowired
	private MailServiceImpl mailService;
	
	@Async
	@PostMapping("/cancelNotificationDelivery")
	public ListenableFuture<ResponseEntity<NotificacionResponse>> cancelNotificationDelivery(
			@RequestBody NotificacionRequest input) {
		LOG.info("METHOD: cancelNotificationDelivery() --PARAMS: notificationRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion() == null) {
			output.setCodigo(Response.NOTIFICATION_CONTENT_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CONTENT_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_CONTENT_ERROR.getIndicador());
		} else if (input.getNotificacion().getIdNotificaciones() == null || 
				input.getNotificacion().getIdNotificaciones() <= 0) {
			output.setCodigo(Response.NOTIFICATION_ID_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_ID_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_ID_ERROR.getIndicador());
		} else {
			Notificacion notificacion = this.notificacionService.findById(input.getNotificacion().getIdNotificaciones());
			
			if (notificacion != null && 
					notificacion.getEstado().getIdEstadoNotificacion().compareTo(
							Constants.ESTADO_NOTIFICACION_PROGRAMADA) == 0) {
				
				notificacion.setEstado(
						new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
								Constants.ESTADO_NOTIFICACION_CANCELADA));
				notificacion = this.notificacionService.save(notificacion);
				
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setNotificacion(this.mapModelo(notificacion));
			} else if (notificacion.getEstado().getIdEstadoNotificacion().compareTo(
							Constants.ESTADO_NOTIFICACION_PROGRAMADA) != 0) {
				output.setCodigo(Response.NOTIFICATION_ALREADY_SENT.getCodigo());
				output.setDescripcion(Response.NOTIFICATION_ALREADY_SENT.getMessage());
				output.setIndicador(Response.NOTIFICATION_ALREADY_SENT.getIndicador());
			} else {
				output.setCodigo(Response.NOTIFICATION_NOT_EXISTS.getCodigo());
				output.setDescripcion(Response.NOTIFICATION_NOT_EXISTS.getMessage());
				output.setIndicador(Response.NOTIFICATION_NOT_EXISTS.getIndicador());
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveNotificationPerId")
	public ListenableFuture<ResponseEntity<NotificacionResponse>> retrieveNotificationPerId(
			@RequestBody NotificacionRequest input) {
		LOG.info("METHOD: retrieveNotificationPerId() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion() == null) {
			output.setCodigo(Response.NOTIFICATION_CONTENT_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CONTENT_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_CONTENT_ERROR.getIndicador());
		} else if (input.getNotificacion().getIdNotificaciones() == null || 
				input.getNotificacion().getIdNotificaciones() <= 0) {
			output.setCodigo(Response.NOTIFICATION_ID_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_ID_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_ID_ERROR.getIndicador());
		} else {
			Notificacion notificacion = this.notificacionService.findById(input.getNotificacion().getIdNotificaciones());
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setNotificacion(this.mapModelo(notificacion));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
		
	}
	
	@Async
	@PostMapping("/sendNotification")
	public ListenableFuture<ResponseEntity<NotificacionResponse>> sendNotification(
			@RequestBody NotificacionRequest input) {
		LOG.info("METHOD: enviarNotificacion() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else if (input.getNotificacion() == null) {
			output.setCodigo(Response.NOTIFICATION_CONTENT_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CONTENT_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_CONTENT_ERROR.getIndicador());
		} else if (input.getNotificacion().getIdNotificaciones() == null ||
				input.getNotificacion().getIdNotificaciones() <= 0) {
			output.setCodigo(Response.NOTIFICATION_ID_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_ID_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_ID_ERROR.getIndicador());
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			Notificacion notificacion = this.notificacionService.findById(input.getNotificacion().getIdNotificaciones());
			List<PaqueteConsumo> listaPaquetes = this.paqueteConsumoService.findPackagesByCompanyAndStatusAndEndDate(
					usuario.getEmpresa().getIdEmpresa(),
					Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO,
					calendar.getTime());
			
			if (listaPaquetes == null || listaPaquetes.isEmpty()) {
				output.setCodigo(Response.PACKAGE_NOT_AVAILABE.getCodigo());
				output.setDescripcion(Response.PACKAGE_NOT_AVAILABE.getMessage());
				output.setIndicador(Response.PACKAGE_NOT_AVAILABE.getIndicador());
			} else {
				List<DetallePaquete> detalles = this.detallePaqueteService.findByPaquete(listaPaquetes.get(0).getPaquete().getIdPaquete());
				
				Integer smsRestantes = 0;
				Integer mailRestantes = 0;
				Integer whatsappRestantes = 0;
				String codigo = "";
				String mailFrom = this.parametroService.findParameterByEmpresaAndName(
						usuario.getEmpresa().getIdEmpresa(),
						Constants.REMITENTE_CORREO).getValor();
				
				for (DetallePaquete detalle: detalles) {
					if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						smsRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoSMS();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						mailRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoMAIL();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						whatsappRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoWhatsapp();;
					}
				}
				
				if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
					if (mailRestantes.compareTo(0) <= 0) {
						output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
						output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
						output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
					} else {
						for (Contacto contacto: notificacion.getDestinatarios()) {
							this.mailService.sendMail(mailFrom, 
									contacto.getEmail(), 
									notificacion.getTitulo(),
									notificacion.getNotificacion());
						}
						notificacion.setCodigo(codigo);
						notificacion.setEstado(
								new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
										Constants.ESTADO_NOTIFICACION_ENVIADA));
						notificacion.setFechaEnvio(new Date());
						notificacion = this.notificacionService.save(notificacion);
						
						output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
						output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
						output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
						output.setNotificacion(this.mapModelo(notificacion));
					}
				} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
					if (smsRestantes.compareTo(0) <= 0) {
						output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
						output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
						output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
					} else {
						for (Contacto contacto: notificacion.getDestinatarios()) {
							codigo = this.smsService.sendSMS(usuario.getEmpresa().getIdEmpresa(), "+502" + contacto.getTelefono(), notificacion.getNotificacion());
							listaPaquetes.get(0).setConsumoSMS(listaPaquetes.get(0).getConsumoSMS() + 1);
						}
						notificacion.setCodigo(codigo);
						notificacion.setEstado(
								new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
										Constants.ESTADO_NOTIFICACION_ENVIADA));
						notificacion.setFechaEnvio(new Date());
						notificacion = this.notificacionService.save(notificacion);
						
						output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
						output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
						output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
						output.setNotificacion(this.mapModelo(notificacion));
					}
				} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
					if (whatsappRestantes.compareTo(0) <= 0) {
						output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
						output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
						output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
					}
				}	
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveRemainingNotifications")
	public ListenableFuture<ResponseEntity<PaqueteConsumoResponse>> retrieveRemainingNotifications(
			@RequestBody NotificacionRequest input
	) {
		LOG.info("METHOD: retrieveRemainingNotifications() --PARAMS: notificacionRequest: " + input);
		PaqueteConsumoResponse output = new PaqueteConsumoResponse();

		if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else if (input.getTipo() == null || input.getTipo() <= 0) {
			output.setCodigo("0070");
			output.setDescripcion("Debe enviar el id del estado del paquete asignado");
			output.setIndicador("ERROR");
		} else if (input.getFechaFin() == null){
			output.setCodigo("0071");
			output.setDescripcion("Debe enviar la fecha de finalizacion de vigencia de paquete");
			output.setIndicador("ERROR");
		} else {
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			List<PaqueteConsumo> paqueteConsumos = this.paqueteConsumoService.findPackagesByCompanyAndStatusAndEndDate(usuario.getEmpresa().getIdEmpresa(), input.getTipo(), input.getFechaFin());
			NotificacionesRestantes notificacionesRestantes = new NotificacionesRestantes();
			notificacionesRestantes.setCantidadCorreo(0);
			notificacionesRestantes.setCantidadMensajes(0);
			notificacionesRestantes.setCantidadWhatsapp(0);

			for(PaqueteConsumo paqueteConsumo: paqueteConsumos) {

				List<DetallePaquete> detalles = this.detallePaqueteService.findByPaquete(paqueteConsumo.getPaquete().getIdPaquete());
				
				for (DetallePaquete detalle: detalles) {
					if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						notificacionesRestantes.setCantidadMensajes(detalle.getCuota() - paqueteConsumo.getConsumoSMS());
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						notificacionesRestantes.setCantidadCorreo(-1);
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						notificacionesRestantes.setCantidadWhatsapp(detalle.getCuota() - paqueteConsumo.getConsumoWhatsapp());
					}
				}
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Notificaciones restantes obtenidas exitosamente");
			output.setIndicador("SUCCESS");
			output.setPaqueteActivo(notificacionesRestantes);
		}		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveNotificationPricePerMonth")
	public ListenableFuture<ResponseEntity<DatosNotificacionPrecioResponse>> retrieveNotificationPricePerMonth(
			@RequestBody NotificacionRequest input
	) {
		LOG.info("METHOD: retrieveNotificationPricePerMonth() --PARAMS: notificacionRequest: " + input);
		DatosNotificacionPrecioResponse output = new DatosNotificacionPrecioResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		List<Notificacion> notificaciones = this.notificacionService.findByCompanyAndStatusPayment(
				usuario.getEmpresa().getIdEmpresa(), 
				false,
				Constants.ESTADO_NOTIFICACION_ENVIADA);
		LOG.info("notificaciones obtenidas");
		BigDecimal tarifa = new BigDecimal(
				this.parametroService.findParameterByEmpresaAndName(
						usuario.getEmpresa().getIdEmpresa(),
						Constants.NOTIFICATION_RATE).getValor());
		output.setDatos(new ArrayList<>());

		for (Notificacion notificacion: notificaciones) {
			if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("MAIL")) {
						modelo.setPrecio(new BigDecimal(0));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
					DatosNotificacionPrecio dato = new DatosNotificacionPrecio();
					dato.setPrecio(new BigDecimal(0));
					dato.setCanal("MAIL");
					output.getDatos().add(dato);
				}
			} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("WHATSAPP")) {
						modelo.setPrecio(modelo.getPrecio().add(tarifa));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
					DatosNotificacionPrecio dato = new DatosNotificacionPrecio();
					dato.setPrecio(tarifa);
					dato.setCanal("WHATSAPP");
					output.getDatos().add(dato);
				}
			} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("SMS")) {
						modelo.setPrecio(modelo.getPrecio().add(tarifa));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
					DatosNotificacionPrecio dato = new DatosNotificacionPrecio();
					dato.setPrecio(tarifa);
					dato.setCanal("SMS");
					output.getDatos().add(dato);
				}
			}
		}

		output.setCodigo("0000");
		output.setDescripcion("Datos obtenidos exitosamente");
		output.setIndicador("SUCCESS");
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveNotificationCountPerMonth")
	public ListenableFuture<ResponseEntity<DatosGraficaResponse>> retrieveNotificationCountPerMonth(
			@RequestBody NotificacionRequest input
	) {
		LOG.info("METHOD: retrieveNotificationCountPerMonth() --PARAMS: notificacionRequest: " + input);
		DatosGraficaResponse output = new DatosGraficaResponse();
		
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		LOG.info("Empresa: " + usuario.getEmpresa());
		List<Notificacion> notificaciones = this.notificacionService.findByCompanyAndStatusPayment(
				usuario.getEmpresa().getIdEmpresa(), 
				false,
				Constants.ESTADO_NOTIFICACION_ENVIADA);
		List<DataGrupoGrafica> graficas = new ArrayList<>();
		LOG.info("Notificaciones: " + notificaciones);

		for (Notificacion notificacion: notificaciones) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(notificacion.getFechaEnvio());
			String mes = Util.convertIntMonthToStringMonth(calendar.get(Calendar.MONTH) + 1);
			boolean existeMES = false;

			for (DataGrupoGrafica grupo: graficas) {
				if (grupo.getName().equalsIgnoreCase(mes)) {

					if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						boolean existeCanal = false;
						for (GraficaBarras data: grupo.getSeries()) {
							if (data.getName().equalsIgnoreCase("CORREO")) {
								data.setValue(data.getValue() + 1);
								existeCanal = true;
							}
						}

						if (!existeCanal) {
							GraficaBarras grafica = new GraficaBarras();
							grafica.setValue(1);
							grafica.setName("CORREO");
							grupo.getSeries().add(grafica);
						}

					} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						boolean existeCanal = false;
						for (GraficaBarras data: grupo.getSeries()) {
							if (data.getName().equalsIgnoreCase("SMS")) {
								data.setValue(data.getValue() + 1);
								existeCanal = true;
							}
						}

						if (!existeCanal) {
							GraficaBarras grafica = new GraficaBarras();
							grafica.setValue(1);
							grafica.setName("SMS");
							grupo.getSeries().add(grafica);
						}
					} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						boolean existeCanal = false;
						for (GraficaBarras data: grupo.getSeries()) {
							if (data.getName().equalsIgnoreCase("WHATSAPP")) {
								data.setValue(data.getValue() + 1);
								existeCanal = true;
							}
						}

						if (!existeCanal) {
							GraficaBarras grafica = new GraficaBarras();
							grafica.setValue(1);
							grafica.setName("WHATSAPP");
							grupo.getSeries().add(grafica);
						}
					}
					existeMES = true;
				}
			}
			if (!existeMES) {

				DataGrupoGrafica grupo = new DataGrupoGrafica();
				grupo.setName(mes);
				grupo.setSeries(new ArrayList<>());

				GraficaBarras grafica = new GraficaBarras();
				grafica.setValue(1);
				if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
					grafica.setName("CORREO");
				} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
					grafica.setName("SMS");
				} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
					grafica.setName("WHATSAPP");
				}
				grupo.getSeries().add(grafica);
				graficas.add(grupo);
			}
		}

		output.setSeries(graficas);
		output.setCodigo("0000");
		output.setDescripcion("Datos obtenidos exitosamente");
		output.setIndicador("SUCCESS");
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByProgrammingDate")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorFechaProgramacion(
			@RequestBody NotificacionRequest input
	) {
		
		LOG.info("METHOD: obtenerNotificacionPorFechaProgramacion() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		Date fechaInicio = null;
		Date fechaFin = null;
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getFechaInicio() == null) {
			output.setCodigo("0035");
			output.setDescripcion("Se debe enviar las fechas para consultar las notificaciones");
			output.setIndicador("ERROR");
		} else if (input.getFechaFin() == null) {
			output.setCodigo("0036");
			output.setDescripcion("Se debe enviar las fechas para consultar las notificaciones");
			output.setIndicador("ERROR");
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo("0037");
			output.setDescripcion("La fecha inical debe ser menor a lafinal");
			output.setIndicador("ERROR");
		} else {
			
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getDayOfMonth());
		    
		    fechaInicio = calendar.getTime();
		    
		    calendar.set(Calendar.HOUR_OF_DAY, 23);
		    calendar.set(Calendar.MINUTE, 59);
		    calendar.set(Calendar.SECOND, 59);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getDayOfMonth());
		    
		    fechaFin = calendar.getTime();

		    LOG.info("fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);
			List<Notificacion> notificaciones = this.notificacionService.findByProgrammingDate(fechaInicio, fechaFin, usuario.getEmpresa().getIdEmpresa());
			
			validarBorrador(
					output,
					notificaciones,
					this.usuarioService.findById(input.getIdUsuario()).getEmpresa().getIdEmpresa());
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByShippingDate")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorFechaShipping(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionPorFechaShipping() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		Date fechaInicio = null;
		Date fechaFin = null;
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getFechaInicio() == null) {
			output.setCodigo("0035");
			output.setDescripcion("Se debe enviar las fechas para consultar las notificaciones");
			output.setIndicador("ERROR");
		} else if (input.getFechaFin() == null) {
			output.setCodigo("0036");
			output.setDescripcion("Se debe enviar las fechas para consultar las notificaciones");
			output.setIndicador("ERROR");
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo("0037");
			output.setDescripcion("La fecha inical debe ser menor a lafinal");
			output.setIndicador("ERROR");
		} else {
			
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getDayOfMonth() + 1);
		    
		    fechaInicio = calendar.getTime();
		    
		    calendar.set(Calendar.HOUR_OF_DAY, 23);
		    calendar.set(Calendar.MINUTE, 59);
		    calendar.set(Calendar.SECOND, 59);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getDayOfMonth() + 1);
		    
		    fechaFin = calendar.getTime();
			
			List<Notificacion> notificaciones = this.notificacionService.findByShippingDate(fechaInicio, fechaFin, usuario.getEmpresa().getIdEmpresa());

			validarBorrador(
					output,
					notificaciones,
					this.usuarioService.findById(input.getIdUsuario()).getEmpresa().getIdEmpresa());
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	private void validarBorrador(
			NotificacionResponse output, 
			List<Notificacion> notificaciones,
			Integer idEmpresa
	) {
		
		for (int x = 0; x < notificaciones.size(); x++) {
			if (notificaciones.get(x).getEstado().getIdEstadoNotificacion().compareTo(Constants.ESTADO_NOTIFICACION_ENVIADA) == 0) {
				notificaciones.remove(x);
			}
		}

		output.setNotificaciones(this.mapparLista(notificaciones));
		output.setCodigo("0000");
		output.setDescripcion("Notificaciones obtenidas exitosamente");
		output.setIndicador("SUCCESS");
	}

	@Async
	@PostMapping("/findByTitle")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorTitulo(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionPorTitulo() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getNotificacion().getTitulo() == null || input.getNotificacion().getTitulo().isEmpty()) { 
			output.setCodigo("0034");
			output.setDescripcion("Se debe enviar el titulo para la busqueda");
			output.setIndicador("ERROR");
		} else {
			
			List<Notificacion> notificaciones = this.notificacionService.findByTitle(input.getNotificacion().getTitulo(), input.getNotificacion().getEstado().getIdEstadoNotificacion(), usuario.getEmpresa().getIdEmpresa());
			
			output.setNotificaciones(this.mapparLista(notificaciones));
			output.setCodigo("0000");
			output.setDescripcion("Notificaciones obtenidas exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByUser")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionesPorUsuario(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionesPorUsuario() --PARAMS: " + input);
		
		NotificacionResponse output = new NotificacionResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getNombreUsuario() == null || input.getNombreUsuario().isEmpty()) {
			output.setCodigo("0033");
			output.setDescripcion("Se debe enviar el usuario para la busqueda");
			output.setIndicador("ERROR");
		} else if (input.getNotificacion() == null ||
				input.getNotificacion().getEstado() == null || input.getNotificacion().getEstado().getIdEstadoNotificacion() < 0) {
			output.setCodigo("0060");
			output.setDescripcion("Debe enviar el estado de la notificacion");
			output.setIndicador("ERROR");
		} else {
			
			List<Notificacion> notificaciones = new ArrayList<>();
			
			if (input.getNotificacion().getEstado().getIdEstadoNotificacion() == 0) {
				notificaciones = this.notificacionService.findByUser(usuario.getEmpresa().getIdEmpresa(), input.getNombreUsuario());
			} else {				
				notificaciones = this.notificacionService.findByUser(usuario.getEmpresa().getIdEmpresa(), input.getNombreUsuario(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
			}
			
			output.setNotificaciones(this.mapparLista(notificaciones));			
			output.setCodigo("0000");
			output.setDescripcion("Notificaciones obtenidas exitosamente");
			output.setIndicador("SUCCESS");
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionesPorEstado(@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionesPorEstado() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getNotificacion().getEstado() == null || input.getNotificacion().getEstado().getIdEstadoNotificacion() <= 0) {
			output.setCodigo("0060");
			output.setDescripcion("Debe enviar el estado de la notificacion");
			output.setIndicador("ERROR");
		}
		
		List<Notificacion> notificaciones = this.notificacionService.findByStatus(input.getNotificacion().getEstado().getIdEstadoNotificacion(), usuario.getEmpresa().getIdEmpresa());
		
		output.setCodigo("0000");
		output.setDescripcion("Notificaciones obtenidas exitosamente");
		output.setIndicador("SUCCESS");
		output.setNotificaciones(this.mapparLista(notificaciones));
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarNotificacion(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: guardarNotificacion() --PARAMS: notificacionRequest: " + input);
		
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion().getTitulo() == null || input.getNotificacion().getTitulo().isEmpty()) {
			output.setCodigo(Response.NOTIFICATION_TITLE_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_TITLE_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_TITLE_ERROR.getIndicador());
		} else if (input.getNotificacion().getFechaEnvio() == null) { // || input.getNotificacion().getFechaEnvio().before(new Date())) {
			output.setCodigo(Response.NOTIFICATION_DATE_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_DATE_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_DATE_ERROR.getIndicador());
		} else if (input.getNotificacion().getNotificacion() == null || input.getNotificacion().getNotificacion().isEmpty()) {
			output.setCodigo(Response.NOTIFICATION_CONTENT_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CONTENT_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_CONTENT_ERROR.getIndicador());
		} else if (input.getNotificacion().getCanal() == null ||
				input.getNotificacion().getCanal().getIdCanal() == null || 
				input.getNotificacion().getCanal().getIdCanal() <= 0) {
			output.setCodigo(Response.NOTIFICATION_CANAL_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CANAL_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_CANAL_ERROR.getIndicador());
		} else {
			
			List<Contacto> contactos = new ArrayList<>();
			for (com.cycsystems.heymebackend.common.Contacto model: input.getNotificacion().getDestinatarios()) {
				Contacto contacto = this.contactoService.findById(model.getIdContacto());
				contactos.add(contacto);
			}
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			
			if (!contactos.isEmpty()) {
				Notificacion notificacion = new Notificacion();
				
				notificacion.setIdNotificaciones(input.getNotificacion().getIdNotificaciones());
				notificacion.setTitulo(input.getNotificacion().getTitulo());
				notificacion.setNotificacion(input.getNotificacion().getNotificacion());
				notificacion.setFechaEnvio(input.getNotificacion().getFechaEnvio());
				
				notificacion.setDestinatarios(contactos);
				notificacion.setEstado(
						new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
								input.getNotificacion().getEstado().getIdEstadoNotificacion(),
								input.getNotificacion().getEstado().getDescripcion()));
				notificacion.setUsuario(usuario);
				notificacion.setEstadoPago(false);
				notificacion.setEmpresa(usuario.getEmpresa());
				
				com.cycsystems.heymebackend.models.entity.Canal canal = new com.cycsystems.heymebackend.models.entity.Canal();
				canal.setIdCanal(input.getNotificacion().getCanal().getIdCanal());
				
				notificacion.setCanal(canal);
				
				this.notificacionService.save(notificacion);
				
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setNotificacion(mapModelo(notificacion));
			} else {
				output.setCodigo(Response.NOTIFICATION_CONTACT_ERROR.getCodigo());
				output.setDescripcion(Response.NOTIFICATION_CONTACT_ERROR.getMessage());
				output.setIndicador(Response.NOTIFICATION_CONTACT_ERROR.getIndicador());
			}
			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private List<com.cycsystems.heymebackend.common.Notificacion> mapparLista(List<Notificacion> notificaciones) {
		
		List<com.cycsystems.heymebackend.common.Notificacion> output = new ArrayList<>();
		for (Notificacion notificacion: notificaciones) {
			com.cycsystems.heymebackend.common.Notificacion modelo = new com.cycsystems.heymebackend.common.Notificacion();
			
			modelo.setTitulo(notificacion.getTitulo());
			
			modelo.setDestinatarios(this.mapearModeloContacto(notificacion.getDestinatarios()));
			modelo.setEstado(
					new EstadoNotificacion(
							notificacion.getEstado().getIdEstadoNotificacion(), 
							notificacion.getEstado().getDescripcion()));
			modelo.setFechaEnvio(notificacion.getFechaEnvio());
			modelo.setFechaProgramacion(notificacion.getFechaProgramacion());
			modelo.setIdNotificaciones(notificacion.getIdNotificaciones());
			modelo.setNotificacion(notificacion.getNotificacion());
			modelo.setEstadoPago(notificacion.getEstadoPago());
			
			com.cycsystems.heymebackend.common.Usuario usuario = new com.cycsystems.heymebackend.common.Usuario();
			usuario.setIdUsuario(notificacion.getUsuario().getIdUsuario());
			usuario.setNombres(notificacion.getUsuario().getNombres());
			usuario.setApellidos(notificacion.getUsuario().getApellidos());
			usuario.setDireccion(notificacion.getUsuario().getDireccion());
			usuario.setEnabled(notificacion.getUsuario().getEnabled());
			usuario.setGenero(
					new Genero(
							notificacion.getUsuario().getGenero().getIdGenero(),
							notificacion.getUsuario().getGenero().getDescripcion()));
			usuario.setImg(notificacion.getUsuario().getImg());
			usuario.setRole(
					new Role(
							notificacion.getUsuario().getRole().getIdRole(),
							notificacion.getUsuario().getRole().getNombre(),
							notificacion.getUsuario().getRole().getDescripcion(),
							notificacion.getUsuario().getRole().getEstado()));
			usuario.setTelefono(notificacion.getUsuario().getTelefono());
			modelo.setUsuario(usuario);
			
			Canal canal = new Canal();
			canal.setIdCanal(notificacion.getCanal().getIdCanal());
			canal.setNombre(notificacion.getCanal().getNombre());
			modelo.setCanal(canal);
			output.add(modelo);
		}
		
		return output;
	}
	
	private com.cycsystems.heymebackend.common.Notificacion mapModelo(Notificacion notificacion) {
		com.cycsystems.heymebackend.common.Notificacion modelo = new com.cycsystems.heymebackend.common.Notificacion();
		
		modelo.setTitulo(notificacion.getTitulo());
		modelo.setDestinatarios(this.mapearModeloContacto(notificacion.getDestinatarios()));
		modelo.setEstado(
				new EstadoNotificacion(
						notificacion.getEstado().getIdEstadoNotificacion(), 
						notificacion.getEstado().getDescripcion()));
		modelo.setFechaEnvio(notificacion.getFechaEnvio());
		modelo.setFechaProgramacion(notificacion.getFechaProgramacion());
		modelo.setIdNotificaciones(notificacion.getIdNotificaciones());
		modelo.setNotificacion(notificacion.getNotificacion());
		
		com.cycsystems.heymebackend.common.Usuario usuario = new com.cycsystems.heymebackend.common.Usuario();
		usuario.setIdUsuario(notificacion.getUsuario().getIdUsuario());
		usuario.setNombres(notificacion.getUsuario().getNombres());
		usuario.setApellidos(notificacion.getUsuario().getApellidos());
		usuario.setDireccion(notificacion.getUsuario().getDireccion());
		usuario.setEnabled(notificacion.getUsuario().getEnabled());
		usuario.setGenero(
				new Genero(
						notificacion.getUsuario().getGenero().getIdGenero(),
						notificacion.getUsuario().getGenero().getDescripcion()));
		usuario.setImg(notificacion.getUsuario().getImg());
		usuario.setRole(
				new Role(
						notificacion.getUsuario().getRole().getIdRole(),
						notificacion.getUsuario().getRole().getNombre(),
						notificacion.getUsuario().getRole().getDescripcion(),
						notificacion.getUsuario().getRole().getEstado()));
		usuario.setTelefono(notificacion.getUsuario().getTelefono());
		modelo.setUsuario(usuario);
		
		Canal canal = new Canal();
		canal.setIdCanal(notificacion.getCanal().getIdCanal());
		canal.setNombre(notificacion.getCanal().getNombre());
		modelo.setCanal(canal);
		
		return modelo;
	}
	
	private List<com.cycsystems.heymebackend.common.Contacto> mapearModeloContacto(List<Contacto> contactos) {
		
		LOG.info("METHOD: mapearModeloContacto() --PARAMS: " + contactos);
		List<com.cycsystems.heymebackend.common.Contacto> modelos = new ArrayList<>();
		
		for (Contacto contacto: contactos) {
			com.cycsystems.heymebackend.common.Contacto modelo = new com.cycsystems.heymebackend.common.Contacto();
			modelo.setIdContacto(contacto.getIdContacto());
			modelo.setNombre(contacto.getNombre());
			modelo.setApellido(contacto.getApellido());
			modelo.setDireccion(contacto.getDireccion());
			modelo.setEmail(contacto.getEmail());
			modelo.setEstado(contacto.getEstado());
			modelo.setTelefono(contacto.getTelefono());
			
			modelo.setProvincia(new com.cycsystems.heymebackend.common.Provincia(
					contacto.getProvincia().getIdProvincia(),
					contacto.getProvincia().getNombre(),
					new Region(
							contacto.getProvincia().getRegion().getIdRegion(),
							contacto.getProvincia().getRegion().getNombre(),
							new Pais(
									contacto.getProvincia().getRegion().getPais().getIdPais(),
									contacto.getProvincia().getRegion().getPais().getNombre()))));
			modelos.add(modelo);
			
		}
		return modelos;
	}

}
