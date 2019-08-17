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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.common.Provincia;
import com.cycsystems.heymebackend.common.Region;
import com.cycsystems.heymebackend.input.ContactoRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.IProvinciaService;
import com.cycsystems.heymebackend.output.ContactoResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/contact")
public class ContactoController {

	private Logger LOG = LogManager.getLogger(ContactoController.class);
	
	@Autowired
	private IProvinciaService provinciaService;
	
	@Autowired
	private IContactoService contactoService;
	
	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos() {
		
		LOG.info("METHOD: obtenerContactos()");
		
		ContactoResponse output = new ContactoResponse();
		
		List<Contacto> contactos = this.contactoService.findAll();
		
		output.setCodigo("0000");
		output.setDescripcion("Contactos obtenidos exitosamente");
		output.setIndicador("SUCCESS");
		output.setContactos(this.mapContacts(contactos));
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> buscarContactoPorId(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: buscarcontactoPorId() --PARAMS: " + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto() == null) {
			output.setCodigo("0058");
			output.setDescripcion("Es necesario enviar los datos del contacto");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getIdContacto() == null || input.getContacto().getIdContacto() <= 0) {
			output.setCodigo("0059");
			output.setDescripcion("Es necesario enviar el id del contacto");
			output.setIndicador("ERROR");
		} else {
			Contacto contacto = this.contactoService.findById(input.getContacto().getIdContacto());
			
			if (contacto == null) {
				output.setCodigo("0060");
				output.setDescripcion("El usuario con el id: " + input.getContacto().getIdContacto() + " no existe");
				output.setIndicador("ERROR");
			} else {
				
				
				
				output.setCodigo("0000");
				output.setDescripcion("Contactos obtenidos exitosamente");
				output.setIndicador("SUCCESS");
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
		
		if (input.getUsuario() == null) {
			output.setCodigo("0045");
			output.setDescripcion("Es necesario enviar los datos del usuario");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getEstado() == null) {
			output.setCodigo("0046");
			output.setDescripcion("Es necesario enviar el estado del usuario");
			output.setIndicador("ERROR");
		} else {
			
			List<Contacto> contactos = this.contactoService.findByStatus(input.getContacto().getEstado());
			
			output.setCodigo("0000");
			output.setDescripcion("Contactos obtenidos exitosamente");
			output.setIndicador("SUCCESS");
			output.setContactos(this.mapContacts(contactos));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByCreationDate")
	public ListenableFuture<ResponseEntity<?>> obtenerContactoPorFecha(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactoPorFecha() --PARAMS: ContactoRequest: " + input);
		
		ContactoResponse output = new ContactoResponse();
		Date fechaInicio = null;
		Date fechaFin = null;
		
		if (input.getFechaInicio() == null) {
			output.setCodigo("0030");
			output.setDescripcion("La fecha de inicio es obligatoria");
			output.setIndicador("ERROR");
		} else if (input.getFechaFin() == null) {
			output.setCodigo("0031");
			output.setDescripcion("La fecha de fin es obligatoria");
			output.setIndicador("ERROR");
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo("0032");
			output.setDescripcion("La fecha de inicio debe ser menor a la fecha de fin");
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
			
		    LOG.info("Fecha Inicio: " + fechaInicio + ", fechaFin: " + fechaFin);
			List<Contacto> contactos = this.contactoService.findByCreationDate(fechaInicio, fechaFin);
			
			output.setContactos(this.mapContacts(contactos));
			output.setCodigo("0000");
			output.setDescripcion("Contactos obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByName")
	public ListenableFuture<ResponseEntity<?>> obtenerContactoPorNombre(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContacto() --PARAMS: ContactoRequest: " + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo("0029");
			output.setDescripcion("El nombre del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else {
			
			List<Contacto> contactos = this.contactoService.findByName(input.getContacto().getNombre());
			
			output.setCodigo("0000");
			output.setDescripcion("Contactos obtenidos exitosamente");
			output.setIndicador("SUCCESS");
			output.setContactos(this.mapContacts(contactos));
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarContacto(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: guardarContacto() --PARAMS: ContactoRequest:" + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo("0023");
			output.setDescripcion("El nombre del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getApellido() == null || input.getContacto().getApellido().isEmpty()) {
			output.setCodigo("0024");
			output.setDescripcion("El apellido del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getDireccion() == null || input.getContacto().getDireccion().isEmpty()) {
			output.setCodigo("0025");
			output.setDescripcion("La direccion del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getProvincia() == null || input.getContacto().getProvincia().getIdProvincia() == null ||
				input.getContacto().getProvincia().getIdProvincia() <= 0) {
			output.setCodigo("0026");
			output.setDescripcion("La region a la que pertence el contacto es obligatoria");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getTelefono() == null || input.getContacto().getTelefono().isEmpty()) {
			output.setCodigo("0027");
			output.setDescripcion("El telefono del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else {
			
			com.cycsystems.heymebackend.models.entity.Provincia provincia = this.provinciaService.findById(input.getContacto().getProvincia().getIdProvincia());
			
			if (provincia == null) {
				output.setCodigo("0028");
				output.setDescripcion("La region enviada no existe, por favor verifique");
				output.setIndicador("ERROR");
			} else {				
				Contacto contacto = new Contacto();
				contacto.setIdContacto(input.getContacto().getIdContacto());
				contacto.setNombre(input.getContacto().getNombre());
				contacto.setApellido(input.getContacto().getApellido());
				contacto.setDireccion(input.getContacto().getDireccion());
				contacto.setEmail(input.getContacto().getEmail());
				contacto.setTelefono(input.getContacto().getTelefono());
				contacto.setEstado(input.getContacto().getEstado());
				contacto.setProvincia(provincia);
				
				contacto = this.contactoService.save(contacto);
				
				output.setCodigo("0000");
				output.setDescripcion("El contacto fue guardado exitosamente");
				output.setIndicador("SUCCESS");
				output.setContacto(this.mapContact(contacto));
			}			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private List<com.cycsystems.heymebackend.common.Contacto> mapContacts(List<Contacto> contactos) {
		
		List<com.cycsystems.heymebackend.common.Contacto> modelos = new ArrayList<>();
		
		for (Contacto contacto: contactos) {
			com.cycsystems.heymebackend.common.Contacto modelo = new com.cycsystems.heymebackend.common.Contacto();
			
			modelo.setIdContacto(contacto.getIdContacto());
			modelo.setNombre(contacto.getNombre());
			modelo.setApellido(contacto.getApellido());
			modelo.setDireccion(contacto.getDireccion());
			modelo.setEmail(contacto.getEmail());
			modelo.setEstado(contacto.getEstado());
			modelo.setProvincia(new Provincia(
					contacto.getProvincia().getIdProvincia(),
					contacto.getProvincia().getNombre(),
					new Region(
							contacto.getProvincia().getRegion().getIdRegion(),
							contacto.getProvincia().getRegion().getNombre(),
							new Pais(
									contacto.getProvincia().getRegion().getPais().getIdPais(),
									contacto.getProvincia().getRegion().getPais().getNombre()))));
			modelo.setTelefono(contacto.getTelefono());
			
			modelos.add(modelo);
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
		modelo.setProvincia(new Provincia(
				contacto.getProvincia().getIdProvincia(),
				contacto.getProvincia().getNombre(),
				new Region(
						contacto.getProvincia().getRegion().getIdRegion(),
						contacto.getProvincia().getRegion().getNombre(),
						new Pais(
								contacto.getProvincia().getRegion().getPais().getIdPais(),
								contacto.getProvincia().getRegion().getPais().getNombre()))));
		
		return modelo;
	}
}
