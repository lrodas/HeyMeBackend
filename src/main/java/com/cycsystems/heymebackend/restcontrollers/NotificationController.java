package com.cycsystems.heymebackend.restcontrollers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.cycsystems.heymebackend.common.Canal;
import com.cycsystems.heymebackend.common.EstadoNotificacion;
import com.cycsystems.heymebackend.common.Genero;
import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.common.Provincia;
import com.cycsystems.heymebackend.common.Region;
import com.cycsystems.heymebackend.common.Role;
import com.cycsystems.heymebackend.input.NotificacionRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.NotificacionResponse;
import com.cycsystems.heymebackend.util.Constants;

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
	
	@Value("${estado.notificacion.programada}")
	private Integer ESTADO_NOTIFICACION_PROGRAMADA;
	
	@Async
	@PostMapping("/findByProgrammingDate")
	public ListenableFuture<ResponseEntity<?>> obtenerNotificacionPorFechaProgramacion(
			@RequestBody NotificacionRequest input) {
		
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
			
			List<Notificacion> notificaciones = this.notificacionService.findByProgrammingDate(fechaInicio, fechaFin);
			
			for (int x = 0; x < notificaciones.size(); x++) {
				if (notificaciones.get(x).getEstado().getIdEstadoNotificacion() == 1) {
					notificaciones.remove(x);
				}
			}
			
			output.setNotificaciones(this.mapparLista(notificaciones));
			output.setCodigo("0000");
			output.setDescripcion("Notificaciones obtenidas exitosamente");
			output.setIndicador("SUCCESS");			
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
			
			for (int x = 0; x < notificaciones.size(); x++) {
				if (notificaciones.get(x).getEstado().getIdEstadoNotificacion() == 1) {
					notificaciones.remove(x);
				}
			}
			
			output.setNotificaciones(this.mapparLista(notificaciones));
			output.setCodigo("0000");
			output.setDescripcion("Notificaciones obtenidas exitosamente");
			output.setIndicador("SUCCESS");			
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
			
			Contacto contacto = this.contactoService.findById(input.getNotificacion().getDestinatario().getIdContacto());
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			
			if (contacto != null) {
				Notificacion notificacion = new Notificacion();
				
				notificacion.setIdNotificaciones(input.getNotificacion().getIdNotificaciones());
				notificacion.setTitulo(input.getNotificacion().getTitulo());
				notificacion.setNotificacion(input.getNotificacion().getNotificacion());
				notificacion.setFechaEnvio(input.getNotificacion().getFechaEnvio());
				notificacion.setDestinatario(contacto);
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
			com.cycsystems.heymebackend.common.Contacto contacto = new com.cycsystems.heymebackend.common.Contacto();
			contacto.setIdContacto(notificacion.getDestinatario().getIdContacto());
			contacto.setNombre(notificacion.getDestinatario().getNombre());
			contacto.setApellido(notificacion.getDestinatario().getApellido());
			contacto.setDireccion(notificacion.getDestinatario().getDireccion());
			contacto.setEmail(notificacion.getDestinatario().getEmail());
			contacto.setEstado(notificacion.getDestinatario().getEstado());
			contacto.setTelefono(notificacion.getDestinatario().getTelefono());
			
			contacto.setProvincia(new Provincia(
					notificacion.getDestinatario().getProvincia().getIdProvincia(),
					notificacion.getDestinatario().getProvincia().getNombre(),
					new Region(
							notificacion.getDestinatario().getProvincia().getRegion().getIdRegion(),
							notificacion.getDestinatario().getProvincia().getRegion().getNombre(),
							new Pais(
									notificacion.getDestinatario().getProvincia().getRegion().getPais().getIdPais(),
									notificacion.getDestinatario().getProvincia().getRegion().getPais().getNombre()))));
			
			modelo.setDestinatario(contacto);
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
		com.cycsystems.heymebackend.common.Contacto contacto = new com.cycsystems.heymebackend.common.Contacto();
		contacto.setIdContacto(notificacion.getDestinatario().getIdContacto());
		contacto.setNombre(notificacion.getDestinatario().getNombre());
		contacto.setApellido(notificacion.getDestinatario().getApellido());
		contacto.setDireccion(notificacion.getDestinatario().getDireccion());
		contacto.setEmail(notificacion.getDestinatario().getEmail());
		contacto.setEstado(notificacion.getDestinatario().getEstado());
		contacto.setTelefono(notificacion.getDestinatario().getTelefono());
		
		contacto.setProvincia(new Provincia(
				notificacion.getDestinatario().getProvincia().getIdProvincia(),
				notificacion.getDestinatario().getProvincia().getNombre(),
				new Region(
						notificacion.getDestinatario().getProvincia().getRegion().getIdRegion(),
						notificacion.getDestinatario().getProvincia().getRegion().getNombre(),
						new Pais(
								notificacion.getDestinatario().getProvincia().getRegion().getPais().getIdPais(),
								notificacion.getDestinatario().getProvincia().getRegion().getPais().getNombre()))));
		
		modelo.setDestinatario(contacto);
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

}
