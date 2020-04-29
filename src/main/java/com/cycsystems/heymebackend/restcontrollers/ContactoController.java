package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.common.Provincia;
import com.cycsystems.heymebackend.common.Region;
import com.cycsystems.heymebackend.input.ContactoRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.IPaisService;
import com.cycsystems.heymebackend.models.service.IProvinciaService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.ContactoResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/contact")
public class ContactoController {

	private Logger LOG = LogManager.getLogger(ContactoController.class);
	
	@Autowired
	private IProvinciaService provinciaService;
	
	@Autowired
	private IContactoService contactoService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPaisService paisService;
	
	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactos()");
		
		ContactoResponse output = new ContactoResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		List<Contacto> contactos = this.contactoService.findAll(usuario.getEmpresa().getIdEmpresa());
		
		output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		output.setContactos(this.mapContacts(contactos));
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> buscarContactoPorId(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: buscarcontactoPorId() --PARAMS: " + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto() == null) {
			output.setCodigo(Response.CONTACT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getIdContacto() == null || input.getContacto().getIdContacto() <= 0) {
			output.setCodigo(Response.CONTACT_ID_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_ID_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_ID_NOT_EMPTY.getIndicador());
		} else {
			Contacto contacto = this.contactoService.findById(input.getContacto().getIdContacto());
			
			if (contacto == null) {
				output.setCodigo(Response.CONTACT_NOT_EXIST.getCodigo());
				output.setDescripcion(Response.CONTACT_NOT_EXIST.getMessage());
				output.setIndicador(Response.CONTACT_NOT_EXIST.getIndicador());
			} else {
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setContacto(this.mapContact(contacto));
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerContactoPorEstado(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactoPorEstado() --PARAMS: contactoRequest:" + input);
		
		ContactoResponse output = new ContactoResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		if (input.getUsuario() == null) {
			output.setCodigo(Response.CONTACT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getEstado() == null) {
			output.setCodigo(Response.CONTACT_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_STATUS_NOT_EMPTY.getIndicador());
		} else {
			
			List<Contacto> contactos = this.contactoService.findByStatus(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getEstado());
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setContactos(this.mapContacts(contactos));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByCreationDate")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactoPorFecha(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactoPorFecha() --PARAMS: ContactoRequest: " + input);
		
		ContactoResponse output = new ContactoResponse();
		Date fechaInicio = null;
		Date fechaFin = null;
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

			List<Contacto> contactos = this.contactoService.findByCreationDate(usuario.getEmpresa().getIdEmpresa(), fechaInicio, fechaFin);
			
			output.setContactos(this.mapContacts(contactos));
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByName")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactoPorNombre(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContacto() --PARAMS: ContactoRequest: " + input);
		ContactoResponse output = new ContactoResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		LOG.info("Usuario obtenido: " + usuario);
		
		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo(Response.CONTACT_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NAME_NOT_EMPTY.getIndicador());
		} else {
			
			List<Contacto> contactos = this.contactoService.findByName(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getNombre());

			contactos = contactos
					.stream()
					.filter(contacto -> usuario.getEmpresa().getIdEmpresa().compareTo(contacto.getEmpresa().getIdEmpresa()) == 0)
					.collect(Collectors.toList());

			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setContactos(this.mapContacts(contactos));
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<ContactoResponse>> guardarContacto(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: guardarContacto() --PARAMS: ContactoRequest:" + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo(Response.CONTACT_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getApellido() == null || input.getContacto().getApellido().isEmpty()) {
			output.setCodigo(Response.CONTACT_LAST_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_LAST_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_LAST_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getDireccion() == null || input.getContacto().getDireccion().isEmpty()) {
			output.setCodigo(Response.CONTACT_ADDRESS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_ADDRESS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_ADDRESS_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getTelefono() == null || input.getContacto().getTelefono().isEmpty()) {
			output.setCodigo(Response.CONTACT_PHONE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_PHONE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_PHONE_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getPais() == null || input.getContacto().getPais().getIdPais() == null ||
					input.getContacto().getPais().getIdPais() <= 0) {
			output.setCodigo(Response.COUNTRY_ID_EMPTY.getCodigo());
			output.setDescripcion(Response.COUNTRY_ID_EMPTY.getMessage());
			output.setIndicador(Response.COUNTRY_ID_EMPTY.getIndicador());
		} else {

			Contacto contacto = new Contacto();
			if (input.getContacto().getProvincia() != null && input.getContacto().getProvincia().getIdProvincia() != null) {
				com.cycsystems.heymebackend.models.entity.Provincia provincia = this.provinciaService.findById(input.getContacto().getProvincia().getIdProvincia());

				if (provincia == null) {
					output.setCodigo(Response.CONTACT_REGION_NOT_EXIST.getCodigo());
					output.setDescripcion(Response.CONTACT_REGION_NOT_EXIST.getMessage());
					output.setIndicador(Response.CONTACT_REGION_NOT_EXIST.getIndicador());

					return new AsyncResult<>(ResponseEntity.ok(output));
				} else {
					contacto.setProvincia(provincia);
				}
			}

			com.cycsystems.heymebackend.models.entity.Pais pais = this.paisService.findCountryById(input.getContacto().getPais().getIdPais());
			contacto.setPais(pais);

			if (input.getContacto().getIdContacto() != null) {
				contacto.setIdContacto(input.getContacto().getIdContacto());
			}
			contacto.setNombre(input.getContacto().getNombre());
			contacto.setApellido(input.getContacto().getApellido());
			contacto.setDireccion(input.getContacto().getDireccion());
			contacto.setEmail(input.getContacto().getEmail());
			contacto.setTelefono(input.getContacto().getTelefono());
			contacto.setEstado(input.getContacto().getEstado());

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			contacto.setUsuario(usuario);
			contacto.setEmpresa(usuario.getEmpresa());

			contacto = this.contactoService.save(contacto);

			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setContacto(this.mapContact(contacto));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private List<com.cycsystems.heymebackend.common.Contacto> mapContacts(List<Contacto> contactos) {
		
		List<com.cycsystems.heymebackend.common.Contacto> modelos = new ArrayList<>();
		
		for (Contacto contacto: contactos) {
			modelos.add(this.mapContact(contacto));
		}
		
		return modelos;
	}
	
	private com.cycsystems.heymebackend.common.Contacto mapContact(Contacto contacto) {
		
		com.cycsystems.heymebackend.common.Contacto modelo = new com.cycsystems.heymebackend.common.Contacto();
		modelo.setIdContacto(contacto.getIdContacto());
		modelo.setNombre(contacto.getNombre());
		modelo.setApellido(contacto.getApellido());
		modelo.setDireccion(contacto.getDireccion());
		modelo.setEmail(contacto.getEmail());
		modelo.setEstado(contacto.getEstado());
		modelo.setTelefono(contacto.getTelefono());
		modelo.setPais(
				new Pais(
						contacto.getPais().getIdPais(),
						contacto.getPais().getNombre(),
						contacto.getPais().getCodigo(),
						contacto.getPais().getEstado()));

		if (contacto.getProvincia() != null) {
			modelo.setProvincia(new Provincia(
					contacto.getProvincia().getIdProvincia(),
					contacto.getProvincia().getNombre(),
					new Region(
							contacto.getProvincia().getRegion().getIdRegion(),
							contacto.getProvincia().getRegion().getNombre(),
							new Pais(
									contacto.getProvincia().getRegion().getPais().getIdPais(),
									contacto.getProvincia().getRegion().getPais().getNombre(),
									contacto.getProvincia().getRegion().getPais().getCodigo(),
									contacto.getProvincia().getRegion().getPais().getEstado()))));
		} else {
			modelo.setProvincia(new Provincia(0, "", new Region(0, "", new Pais())));
		}
		
		return modelo;
	}
}
