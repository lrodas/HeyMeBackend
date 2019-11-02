package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.*;
import com.cycsystems.heymebackend.input.NotificacionRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.*;
import com.cycsystems.heymebackend.output.DatosGraficaResponse;
import com.cycsystems.heymebackend.output.DatosNotificacionPrecioResponse;
import com.cycsystems.heymebackend.output.NotificacionResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	private ITwilioService twilioService;

	@Autowired
	private IEmpresaService empresaService;
	
	@Value("${estado.notificacion.programada}")
	private Integer ESTADO_NOTIFICACION_PROGRAMADA;

	@Value("${estado.notificacion.enviada}")
	private Integer ESTADO_NOTIFICACION_ENVIADA;

	@Value("${estado.notificacion.creada}")
	private Integer ESTADO_NOTIFICACION_CREADA;

	@Value("${canal.sms}")
	private Integer CANAL_SMS;

	@Value("${canal.mail}")
	private Integer CANAL_MAIL;

	@Value("${canal.whatsapp}")
	private Integer CANAL_WHATSAPP;

	@Async
	@PostMapping("/retrieveNotificationPricePerMonth")
	public ListenableFuture<ResponseEntity<DatosNotificacionPrecioResponse>> retrieveNotificationPricePerMonth(
			@RequestBody NotificacionRequest input
	) {
		LOG.info("METHOD: retrieveNotificationPricePerMonth() --PARAMS: notificacionRequest: " + input);
		DatosNotificacionPrecioResponse output = new DatosNotificacionPrecioResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		List<Notificacion> notificaciones = this.notificacionService.findByCompanyAndStatusPayment(usuario.getEmpresa().getIdEmpresa(), false);
		BigDecimal tarifa = usuario.getEmpresa().getTarifa();
		output.setDatos(new ArrayList<>());

		for (Notificacion notificacion: notificaciones) {
			DatosNotificacionPrecio dato = new DatosNotificacionPrecio();
			if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_MAIL) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("MAIL")) {
						dato.setPrecio(new BigDecimal(0));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
					dato.setPrecio(new BigDecimal(0));
					dato.setCanal("MAIL");
					output.getDatos().add(dato);
				}
			} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_WHATSAPP) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("WHATSAPP")) {
						dato.setPrecio(dato.getPrecio().add(tarifa));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
					dato.setPrecio(tarifa);
					dato.setCanal("WHATSAPP");
					output.getDatos().add(dato);
				}
			} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_SMS) == 0) {
				boolean existeCanal = false;
				for (DatosNotificacionPrecio modelo: output.getDatos()) {
					if (modelo.getCanal().equalsIgnoreCase("SMS")) {
						dato.setPrecio(dato.getPrecio().add(tarifa));
						existeCanal = true;
					}
				}

				if (!existeCanal) {
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
		List<Notificacion> notificaciones = this.notificacionService.findByCompanyAndStatus(usuario.getEmpresa().getIdEmpresa(), this.ESTADO_NOTIFICACION_ENVIADA);
		List<DataGrupoGrafica> graficas = new ArrayList<>();

		for (Notificacion notificacion: notificaciones) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(notificacion.getFechaEnvio());
			String mes = Util.convertIntMonthToStringMonth(calendar.get(Calendar.MONTH) + 1);
			boolean existeMES = false;

			for (DataGrupoGrafica grupo: graficas) {
				if (grupo.getName().equalsIgnoreCase(mes)) {

					if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_MAIL) == 0) {
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

					} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_SMS) == 0) {
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
					} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_WHATSAPP) == 0) {
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
				if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_MAIL) == 0) {
					grafica.setName("CORREO");
				} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_SMS) == 0) {
					grafica.setName("SMS");
				} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_WHATSAPP) == 0) {
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
			List<Notificacion> notificaciones = this.notificacionService.findByProgrammingDate(fechaInicio, fechaFin);

			validarBorrador(output, notificaciones);
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
			
			List<Notificacion> notificaciones = this.notificacionService.findByShippingDate(fechaInicio, fechaFin);

			validarBorrador(output, notificaciones);
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	private void validarBorrador(NotificacionResponse output, List<Notificacion> notificaciones) {
		for (int x = 0; x < notificaciones.size(); x++) {
			if (notificaciones.get(x).getEstado().getIdEstadoNotificacion() == this.ESTADO_NOTIFICACION_CREADA) {
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
		
		if (input.getNotificacion().getTitulo() == null || input.getNotificacion().getTitulo().isEmpty()) { 
			output.setCodigo("0034");
			output.setDescripcion("Se debe enviar el titulo para la busqueda");
			output.setIndicador("ERROR");
		} else {
			
			List<Notificacion> notificaciones = this.notificacionService.findByTitle(input.getNotificacion().getTitulo(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
			
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
				notificaciones = this.notificacionService.findByUser(input.getNombreUsuario());
			} else {				
				notificaciones = this.notificacionService.findByUser(input.getNombreUsuario(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
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
		
		if (input.getNotificacion().getEstado() == null || input.getNotificacion().getEstado().getIdEstadoNotificacion() <= 0) {
			output.setCodigo("0060");
			output.setDescripcion("Debe enviar el estado de la notificacion");
			output.setIndicador("ERROR");
		}
		
		List<Notificacion> notificaciones = this.notificacionService.findByStatus(input.getNotificacion().getEstado().getIdEstadoNotificacion());
		
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
			output.setCodigo("0018");
			output.setDescripcion("Debe enviar el titulo de la notificacion");
			output.setIndicador("ERROR");
		} else if (input.getNotificacion().getFechaEnvio() == null) { // || input.getNotificacion().getFechaEnvio().before(new Date())) {
			output.setCodigo("0019");
			output.setDescripcion("se debe enviar la fecha en que se enviara la notificacion");
			output.setIndicador("ERROR");
		} else if (input.getNotificacion().getNotificacion() == null || input.getNotificacion().getNotificacion().isEmpty()) {
			output.setCodigo("0021");
			output.setDescripcion("Se debe enviar el contenido de la notificacion");
			output.setIndicador("ERROR");
		} else if (input.getNotificacion().getCanal() == null ||
				input.getNotificacion().getCanal().getIdCanal() == null || 
				input.getNotificacion().getCanal().getIdCanal() <= 0) {
			output.setCodigo("0055");
			output.setDescripcion("Se debe enviar el canal");
			output.setIndicador("ERROR");
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
				
				com.cycsystems.heymebackend.models.entity.Canal canal = new com.cycsystems.heymebackend.models.entity.Canal();
				canal.setIdCanal(input.getNotificacion().getCanal().getIdCanal());
				
				notificacion.setCanal(canal);
				
				this.notificacionService.save(notificacion);
				
				output.setCodigo("0000");
				output.setDescripcion("Notificacion guardada exitosamente");
				output.setIndicador("SUCCESS");
				output.setNotificacion(mapModelo(notificacion));
			} else {
				output.setCodigo("0022");
				output.setDescripcion("El contacto enviado no existe");
				output.setIndicador("ERROR");
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
			
			modelo.setProvincia(new Provincia(
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
