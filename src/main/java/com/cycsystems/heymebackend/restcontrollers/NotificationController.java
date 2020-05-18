 package com.cycsystems.heymebackend.restcontrollers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cycsystems.heymebackend.common.DataGrupoGrafica;
import com.cycsystems.heymebackend.common.DatosNotificacionPrecio;
import com.cycsystems.heymebackend.common.GraficaBarras;
import com.cycsystems.heymebackend.common.NotificacionesRestantes;
import com.cycsystems.heymebackend.convert.CNotificacion;
import com.twilio.rest.api.v2010.account.Message;
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
	
	private final INotificacionService notificacionService;
	private final IContactoService contactoService;
	private final IUsuarioService usuarioService;
	private final IParametroService parametroService;
	private final IPaqueteConsumoService paqueteConsumoService;
	private final IDetallePaqueteService detallePaqueteService;
	private final SMSServiceImpl smsService;
	private final MailServiceImpl mailService;

	@Autowired
	public NotificationController(INotificacionService notificacionService, IContactoService contactoService, IUsuarioService usuarioService, IParametroService parametroService, IPaqueteConsumoService paqueteConsumoService, IDetallePaqueteService detallePaqueteService, SMSServiceImpl smsService, MailServiceImpl mailService) {
		this.notificacionService = notificacionService;
		this.contactoService = contactoService;
		this.usuarioService = usuarioService;
		this.parametroService = parametroService;
		this.paqueteConsumoService = paqueteConsumoService;
		this.detallePaqueteService = detallePaqueteService;
		this.smsService = smsService;
		this.mailService = mailService;
	}

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
								Constants.ESTADO_NOTIFICACION_CANCELADA, ""));
				notificacion = this.notificacionService.save(notificacion);
				
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setNotificacion(CNotificacion.EntityToModel(notificacion));

			} else if (notificacion != null &&
					notificacion.getEstado().getIdEstadoNotificacion().compareTo(
							Constants.ESTADO_NOTIFICACION_CANCELADA) != 0) {
				output.setCodigo(Response.NOTIFICATION_ALREADY_CANCELED.getCodigo());
				output.setDescripcion(Response.NOTIFICATION_ALREADY_CANCELED.getMessage());
				output.setIndicador(Response.NOTIFICATION_ALREADY_CANCELED.getIndicador());
			} else if (notificacion != null) {
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
			output.setNotificacion(CNotificacion.EntityToModel(notificacion));
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

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Notificacion notificacion = this.notificacionService.findById(input.getNotificacion().getIdNotificaciones());

				if (notificacion == null) {
					output.setCodigo(Response.NOTIFICATION_NOT_EXISTS.getCodigo());
					output.setDescripcion(Response.NOTIFICATION_NOT_EXISTS.getMessage());
					output.setIndicador(Response.NOTIFICATION_NOT_EXISTS.getIndicador());
				} else {
					List<PaqueteConsumo> listaPaquetes = this.paqueteConsumoService.findPackagesByCompanyAndStatus(
							usuario.getEmpresa().getIdEmpresa(),
							Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO);

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

						for (DetallePaquete detalle : detalles) {
							if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
								smsRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoSMS();
							} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
								mailRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoMAIL();
							} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
								whatsappRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoWhatsapp();
							}
						}

						if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
							if (mailRestantes.compareTo(0) <= 0) {
								output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
								output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
								output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
							} else {
								for (Contacto contacto : notificacion.getDestinatarios()) {
									this.mailService.sendMail(mailFrom,
											contacto.getEmail(),
											notificacion.getTitulo(),
											notificacion.getNotificacion());
								}
								notificacion.setCodigo(codigo);

								notificacion.setEstado(
										new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
												Constants.ESTADO_NOTIFICACION_ENVIADA, ""));
								notificacion.setFechaEnvio(new Date());
							}
						} else if (notificacion.getCanal().getIdCanal().intValue() == Constants.CANAL_SMS ||
								notificacion.getCanal().getIdCanal().intValue() == Constants.CANAL_WHATSAPP) {
							if (smsRestantes.compareTo(0) <= 0) {
								output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
								output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
								output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
							} else {
								Message message = null;

								for (Contacto contacto : notificacion.getDestinatarios()) {
									String telefono;

									if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
										telefono = "whatsapp:"+contacto.getPais().getCodigo() + contacto.getTelefono();
									} else {
										telefono = contacto.getPais().getCodigo() + contacto.getTelefono();
									}
									message = this.smsService.sendSMS(usuario.getEmpresa().getIdEmpresa(), telefono, notificacion.getNotificacion());
									listaPaquetes.get(0).setConsumoSMS(listaPaquetes.get(0).getConsumoSMS() + 1);
								}

								assert message != null;
								notificacion.setCodigo(message.getSid());

								LOG.info("estatus en el controlador: " + message.getStatus());
								notificacion.setEstado(
										new com.cycsystems.heymebackend.models.entity.EstadoNotificacion(
												Constants.ESTADO_NOTIFICACION_ENVIADA, ""));
								notificacion.setFechaEnvio(new Date());

							}
						} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
							if (whatsappRestantes.compareTo(0) <= 0) {
								output.setCodigo(Response.NOTIFICATIONS_NOT_AVAILABLE.getCodigo());
								output.setDescripcion(Response.NOTIFICATIONS_NOT_AVAILABLE.getMessage());
								output.setIndicador(Response.NOTIFICATIONS_NOT_AVAILABLE.getIndicador());
							}
						}

						notificacion = this.notificacionService.save(notificacion);

						output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
						output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
						output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
						output.setNotificacion(CNotificacion.EntityToModel(notificacion));
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
			output.setCodigo(Response.PACKAGE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.PACKAGE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.PACKAGE_STATUS_NOT_EMPTY.getIndicador());
		} else if (input.getFechaFin() == null){
			output.setCodigo(Response.PACKAGE_END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.PACKAGE_END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.PACKAGE_END_DATE_NOT_EMPTY.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			List<PaqueteConsumo> paqueteConsumos = this.paqueteConsumoService.findPackagesByCompanyAndStatus(usuario.getEmpresa().getIdEmpresa(), input.getTipo());
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
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
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

		output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
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
		output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByProgrammingDate")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorFechaProgramacion(
			@RequestBody NotificacionRequest input
	) {
		
		LOG.info("METHOD: obtenerNotificacionPorFechaProgramacion() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getFechaInicio() == null) {
			output.setCodigo(Response.START_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.START_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.START_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaFin() == null) {
			output.setCodigo(Response.END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.END_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo(Response.START_DATE_BEFORE_END_DATE.getCodigo());
			output.setDescripcion(Response.START_DATE_BEFORE_END_DATE.getMessage());
			output.setIndicador(Response.START_DATE_BEFORE_END_DATE.getIndicador());
		} else {
			
			Date fechaInicio = Util.mapDate(input.getFechaInicio());
		    Date fechaFin = Util.mapDate(input.getFechaFin());

			List<com.cycsystems.heymebackend.common.Notificacion> notificaciones = this.notificacionService
					.findByProgrammingDate(fechaInicio, fechaFin, usuario.getEmpresa().getIdEmpresa())
					.stream()
					.map(CNotificacion::EntityToModel)
					.collect(Collectors.toList());

			output.setNotificaciones(notificaciones);
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByShippingDate")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorFechaShipping(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionPorFechaShipping() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();

		if (input.getFechaInicio() == null) {
			output.setCodigo(Response.START_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.START_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.START_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaFin() == null) {
			output.setCodigo(Response.END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.END_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo(Response.START_DATE_BEFORE_END_DATE.getCodigo());
			output.setDescripcion(Response.START_DATE_BEFORE_END_DATE.getMessage());
			output.setIndicador(Response.START_DATE_BEFORE_END_DATE.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Date fechaInicio = Util.mapDate(input.getFechaInicio());
				Date fechaFin = Util.mapDate(input.getFechaFin());

				List<com.cycsystems.heymebackend.common.Notificacion> notificaciones = this.notificacionService
						.findByShippingDate(fechaInicio, fechaFin, usuario.getEmpresa().getIdEmpresa())
						.stream()
						.map(CNotificacion::EntityToModel)
						.collect(Collectors.toList());

				output.setNotificaciones(notificaciones);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findByTitle")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorTitulo(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionPorTitulo() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion().getTitulo() == null || input.getNotificacion().getTitulo().isEmpty()) { 
			output.setCodigo(Response.NOTIFICATION_TITLE_ERROR.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_TITLE_ERROR.getMessage());
			output.setIndicador(Response.NOTIFICATION_TITLE_ERROR.getIndicador());
		} if (input.getNotificacion().getEstado() == null ||
			input.getNotificacion().getEstado().getIdEstadoNotificacion() == null ||
			input.getNotificacion().getEstado().getIdEstadoNotificacion() <= 0) {
			output.setCodigo(Response.NOTIFICATION_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.NOTIFICATION_STATUS_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				List<com.cycsystems.heymebackend.common.Notificacion> notificaciones = this.notificacionService
						.findByTitle(input.getNotificacion().getTitulo(),
								input.getNotificacion().getEstado().getIdEstadoNotificacion(),
								usuario.getEmpresa().getIdEmpresa())
						.stream()
						.map(CNotificacion::EntityToModel)
						.collect(Collectors.toList());

				output.setNotificaciones(notificaciones);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByUser")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionesPorUsuario(
			@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionesPorUsuario() --PARAMS: " + input);
		
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNombreUsuario() == null || input.getNombreUsuario().isEmpty()) {
			output.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			output.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else if (input.getNotificacion() == null ||
				input.getNotificacion().getEstado() == null || input.getNotificacion().getEstado().getIdEstadoNotificacion() < 0) {
			output.setCodigo(Response.NOTIFICATION_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.NOTIFICATION_STATUS_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<com.cycsystems.heymebackend.common.Notificacion> notificaciones;

				if (input.getNotificacion().getEstado().getIdEstadoNotificacion() == 0) {
					notificaciones = this.notificacionService
							.findByUser(usuario.getEmpresa().getIdEmpresa(),
									input.getNombreUsuario())
							.stream()
							.map(CNotificacion::EntityToModel)
							.collect(Collectors.toList());
				} else {
					notificaciones = this.notificacionService
							.findByUser(usuario.getEmpresa().getIdEmpresa(),
									input.getNombreUsuario(),
									input.getNotificacion().getEstado().getIdEstadoNotificacion())
							.stream()
							.map(CNotificacion::EntityToModel)
							.collect(Collectors.toList());
				}

				output.setNotificaciones(notificaciones);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionesPorEstado(@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: obtenerNotificacionesPorEstado() --PARAMS: notificacionRequest: " + input);
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion().getEstado() == null || input.getNotificacion().getEstado().getIdEstadoNotificacion() <= 0) {
			output.setCodigo(Response.NOTIFICATION_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.NOTIFICATION_STATUS_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<com.cycsystems.heymebackend.common.Notificacion> notificaciones = this.notificacionService
						.findByStatus(input.getNotificacion().getEstado().getIdEstadoNotificacion(),
								usuario.getEmpresa().getIdEmpresa())
						.stream()
						.map(CNotificacion::EntityToModel)
						.collect(Collectors.toList());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setNotificaciones(notificaciones);
			}
		}
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
		} else if (input.getNotificacion().getDestinatarios() == null ||
				input.getNotificacion().getDestinatarios().isEmpty()) {
			output.setCodigo(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<Contacto> contactos = new ArrayList<>();
				for (com.cycsystems.heymebackend.common.Contacto model : input.getNotificacion().getDestinatarios()) {
					Contacto contacto = this.contactoService.findById(model.getIdContacto());
					if (contacto != null) contactos.add(contacto);
				}

				if (!contactos.isEmpty()) {

					Notificacion notificacion = CNotificacion.ModelToEntity(input.getNotificacion());

					notificacion.setDestinatarios(contactos);
					notificacion.setUsuario(usuario);
					notificacion.setEstadoPago(false);
					notificacion.setEmpresa(usuario.getEmpresa());

					notificacion = this.notificacionService.save(notificacion);

					output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					output.setNotificacion(CNotificacion.EntityToModel(notificacion));
				} else {
					output.setCodigo(Response.NOTIFICATION_CONTACT_ERROR.getCodigo());
					output.setDescripcion(Response.NOTIFICATION_CONTACT_ERROR.getMessage());
					output.setIndicador(Response.NOTIFICATION_CONTACT_ERROR.getIndicador());
				}
			}

		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

}
