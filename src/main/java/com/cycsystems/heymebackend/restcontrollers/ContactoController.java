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
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.input.ContactoRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Region;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.IRegionService;
import com.cycsystems.heymebackend.output.ContactoResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/contact")
public class ContactoController {

	private Logger LOG = LogManager.getLogger(ContactoController.class);
	
	@Autowired
	private IRegionService regionService;
	
	@Autowired
	private IContactoService contactoService;
	
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
		} else if (input.getContacto().getRegion() == null || input.getContacto().getRegion().getIdRegion() == null ||
				input.getContacto().getRegion().getIdRegion() <= 0) {
			output.setCodigo("0026");
			output.setDescripcion("La region a la que pertence el contacto es obligatoria");
			output.setIndicador("ERROR");
		} else if (input.getContacto().getTelefono() == null || input.getContacto().getTelefono().isEmpty()) {
			output.setCodigo("0027");
			output.setDescripcion("El telefono del contacto es obligatorio");
			output.setIndicador("ERROR");
		} else {
			
			Region region = this.regionService.findById(input.getContacto().getRegion().getIdRegion());
			
			if (region == null) {
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
				contacto.setEstado(true);
				contacto.setRegion(region);
				
				this.contactoService.save(contacto);
				
				output.setCodigo("0000");
				output.setDescripcion("El contacto fue guardado exitosamente");
				output.setIndicador("SUCCESS");
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
			
			com.cycsystems.heymebackend.common.Region region = new com.cycsystems.heymebackend.common.Region();
			region.setIdRegion(contacto.getRegion().getIdRegion());
			region.setNombre(contacto.getRegion().getNombre());
			
			Pais pais = new Pais();
			pais.setIdPais(contacto.getRegion().getPais().getIdPais());
			pais.setNombre(contacto.getRegion().getPais().getNombre());
			
			region.setPais(pais);
			
			modelo.setRegion(region);
			modelo.setTelefono(contacto.getTelefono());
			
			modelos.add(modelo);
			
		}
		
		return modelos;
	}
}
