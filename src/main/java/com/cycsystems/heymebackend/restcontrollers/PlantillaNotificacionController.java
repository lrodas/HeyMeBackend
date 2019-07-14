package com.cycsystems.heymebackend.restcontrollers;

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

import com.cycsystems.heymebackend.input.PlantillaNotificacionRequest;
import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;
import com.cycsystems.heymebackend.models.service.IPlantillaNotificacionService;
import com.cycsystems.heymebackend.output.PlantillaNotificacionResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/template")
public class PlantillaNotificacionController {

	private Logger LOG = LogManager.getLogger(PlantillaNotificacionController.class);
	
	@Autowired
	private IPlantillaNotificacionService service;
	
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillasPorEstado(
			@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: obtenerPlantillasPorEstado() --PARAMS: PlantillaNotificacionRequest: " + input);
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla() == null) {
			output.setCodigo("0042");
			output.setDescripcion("Debe enviar la plantilla");
			output.setIndicador("ERROR");
		} else if (input.getPlantilla().getEstado() == null) {
			output.setCodigo("0044");
			output.setDescripcion("Debe enviar el estado de la plantilla");
			output.setIndicador("ERROR");
		} else {
			
			output.setCodigo("0000");
			output.setDescripcion("Plantillas obtenidas exitosamente");
			output.setIndicador("SUCCESS");
			
			List<PlantillaNotificacion> listaPlantillas = this.service.findByEstado(input.getPlantilla().getEstado());
			
			if (listaPlantillas != null) {
				for (PlantillaNotificacion plantilla: listaPlantillas) {
					com.cycsystems.heymebackend.common.PlantillaNotificacion modelo = new com.cycsystems.heymebackend.common.PlantillaNotificacion();
					
					modelo.setIdPlantillaNotificacion(plantilla.getIdPlantillaNotificacion());
					modelo.setTitulo(plantilla.getTitulo());
					modelo.setPlantilla(plantilla.getPlantilla());
					modelo.setEstado(plantilla.getEstado());
					
					output.getPlantillas().add(modelo);
				}
			}
			
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findByTitle")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillasPorTitulo(
			@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: obtenerPlantillasPorTitulo() --PARAMS: PlantillaNotificacionRequest: " + input);
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla() == null) {
			output.setCodigo("0042");
			output.setDescripcion("Debe enviar la plantilla");
			output.setIndicador("ERROR");
		} else if (input.getPlantilla().getTitulo() == null || input.getPlantilla().getTitulo().isEmpty()) {
			output.setCodigo("0043");
			output.setDescripcion("Debe enviar el titulo de la plantilla");
			output.setIndicador("ERROR");
		} else {
			
			output.setCodigo("0000");
			output.setDescripcion("Plantillas obtenidas exitosamente");
			output.setIndicador("SUCCESS");
			
			List<PlantillaNotificacion> listaPlantillas = this.service.findByTitle(input.getPlantilla().getTitulo());
			
			if (listaPlantillas != null) {
				for (PlantillaNotificacion plantilla: listaPlantillas) {
					com.cycsystems.heymebackend.common.PlantillaNotificacion modelo = new com.cycsystems.heymebackend.common.PlantillaNotificacion();
					
					modelo.setIdPlantillaNotificacion(plantilla.getIdPlantillaNotificacion());
					modelo.setTitulo(plantilla.getTitulo());
					modelo.setPlantilla(plantilla.getPlantilla());
					modelo.setEstado(plantilla.getEstado());
					
					output.getPlantillas().add(modelo);
				}
			}
			
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillas() {
		
		LOG.info("METHOD: obtenerPlantillas()");
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		output.setCodigo("0000");
		output.setDescripcion("Plantillas obtenidas exitosamente");
		output.setIndicador("SUCCESS");
		
		List<PlantillaNotificacion> listaPlantillas = this.service.findAll();
		
		if (listaPlantillas != null) {
			for (PlantillaNotificacion plantilla: listaPlantillas) {
				com.cycsystems.heymebackend.common.PlantillaNotificacion modelo = new com.cycsystems.heymebackend.common.PlantillaNotificacion();
				
				modelo.setIdPlantillaNotificacion(plantilla.getIdPlantillaNotificacion());
				modelo.setTitulo(plantilla.getTitulo());
				modelo.setPlantilla(plantilla.getPlantilla());
				modelo.setEstado(plantilla.getEstado());
				
				output.getPlantillas().add(modelo);
			}
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarPlantilla(@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: guardarPlantilla() --PARAMS: plantillaNotificacionRequest: " + input);
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla() == null) {
			output.setCodigo("0038");
			output.setDescripcion("Debe ingresar los datos de la plantilla");
			output.setIndicador("ERROR");
		} else if (input.getPlantilla().getTitulo() == null || input.getPlantilla().getTitulo().isEmpty()) {
			output.setCodigo("0039");
			output.setDescripcion("Debe ingresar el titulo para la plantilla");
			output.setIndicador("ERROR");
		} else if (input.getPlantilla().getPlantilla() == null || input.getPlantilla().getPlantilla().isEmpty()) {
			output.setCodigo("0040");
			output.setDescripcion("Debe ingresar el contenido para la plantilla");
			output.setIndicador("ERROR");
		} else if (input.getPlantilla().getEstado() == null) {
			output.setCodigo("0041");
			output.setDescripcion("Debe ingresar el estado de la plantilla");
			output.setIndicador("ERROR");
		} else {
			
			PlantillaNotificacion plantilla = new PlantillaNotificacion();
			plantilla.setIdPlantillaNotificacion(input.getPlantilla().getIdPlantillaNotificacion());
			plantilla.setTitulo(input.getPlantilla().getTitulo());
			plantilla.setPlantilla(input.getPlantilla().getPlantilla());
			plantilla.setEstado(input.getPlantilla().getEstado());
			
			this.service.save(plantilla);
			
			output.setCodigo("0000");
			output.setDescripcion("Plantilla guardada exitosamente");
			output.setIndicador("SUCCESS");
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
}
