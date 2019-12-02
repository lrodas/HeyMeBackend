package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Canal;
import com.cycsystems.heymebackend.input.PlantillaNotificacionRequest;
import com.cycsystems.heymebackend.models.entity.PlantillaNotificacion;
import com.cycsystems.heymebackend.models.service.IPlantillaNotificacionService;
import com.cycsystems.heymebackend.output.PlantillaNotificacionResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
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

import java.util.List;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/template")
public class PlantillaNotificacionController {

	private Logger LOG = LogManager.getLogger(PlantillaNotificacionController.class);
	
	@Autowired
	private IPlantillaNotificacionService service;
	
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillaPorId(
			@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: obtenerPlantillaPorId() --PARAMS: PlantillaNotificacionRequest: " + input);
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla().getIdPlantillaNotificacion() == null || input.getPlantilla().getIdPlantillaNotificacion() <= 0) {
			output.setCodigo(Response.TEMPLATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_NOT_EMPTY.getIndicador());
		} else {
			
			PlantillaNotificacion plantilla = this.service.findById(input.getPlantilla().getIdPlantillaNotificacion());
			
			if (plantilla == null) {
				
				output.setCodigo(Response.TEMPLATE_NOT_EXISTS.getCodigo());
				output.setDescripcion(Response.TEMPLATE_NOT_EXISTS.getMessage());
				output.setIndicador(Response.TEMPLATE_NOT_EXISTS.getIndicador());
				
			} else {
				
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setPlantilla(this.mapearModelo(plantilla));
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
		
	}
	
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillasPorEstado(
			@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: obtenerPlantillasPorEstado() --PARAMS: PlantillaNotificacionRequest: " + input);
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla() == null) {
			output.setCodigo(Response.TEMPLATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_NOT_EMPTY.getIndicador());
		} else if (input.getPlantilla().getEstado() == null) {
			output.setCodigo(Response.TEMPLATE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_STATUS_NOT_EMPTY.getIndicador());
		} else {
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			
			List<PlantillaNotificacion> listaPlantillas = this.service.findByEstado(input.getPlantilla().getEstado());
			
			if (listaPlantillas != null) {
				for (PlantillaNotificacion plantilla: listaPlantillas) {
					output.getPlantillas().add(this.mapearModelo(plantilla));
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
			output.setCodigo(Response.TEMPLATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_NOT_EMPTY.getIndicador());
		} else if (input.getPlantilla().getTitulo() == null || input.getPlantilla().getTitulo().isEmpty()) {
			output.setCodigo(Response.TEMPLATE_TITLE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_TITLE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_TITLE_NOT_EMPTY.getIndicador());
		} else {
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			
			List<PlantillaNotificacion> listaPlantillas = this.service.findByTitle(input.getPlantilla().getTitulo());
			
			if (listaPlantillas != null) {
				for (PlantillaNotificacion plantilla: listaPlantillas) {
					output.getPlantillas().add(this.mapearModelo(plantilla));
				}
			}
			
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerPlantillas() {
		
		LOG.info("METHOD: obtenerPlantillas()");
		
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		
		List<PlantillaNotificacion> listaPlantillas = this.service.findAll();
		
		if (listaPlantillas != null) {
			for (PlantillaNotificacion plantilla: listaPlantillas) {
				output.getPlantillas().add(this.mapearModelo(plantilla));
			}
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarPlantilla(@RequestBody PlantillaNotificacionRequest input) {
		
		LOG.info("METHOD: guardarPlantilla() --PARAMS: plantillaNotificacionRequest: " + input);
		PlantillaNotificacionResponse output = new PlantillaNotificacionResponse();
		
		if (input.getPlantilla() == null) {
			output.setCodigo(Response.TEMPLATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_NOT_EMPTY.getIndicador());
		} else if (input.getPlantilla().getTitulo() == null || input.getPlantilla().getTitulo().isEmpty()) {
			output.setCodigo(Response.TEMPLATE_TITLE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_TITLE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_TITLE_NOT_EMPTY.getIndicador());
		} else if (input.getPlantilla().getPlantilla() == null || input.getPlantilla().getPlantilla().isEmpty()) {
			output.setCodigo(Response.TEMPLATE_CONTENT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_CONTENT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_CONTENT_NOT_EMPTY.getIndicador());
		} else if (input.getPlantilla().getEstado() == null) {
			output.setCodigo(Response.TEMPLATE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.TEMPLATE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.TEMPLATE_STATUS_NOT_EMPTY.getIndicador());
		} else {
			
			PlantillaNotificacion plantilla = new PlantillaNotificacion();
			plantilla.setIdPlantillaNotificacion(input.getPlantilla().getIdPlantillaNotificacion());
			plantilla.setTitulo(input.getPlantilla().getTitulo());
			plantilla.setPlantilla(input.getPlantilla().getPlantilla());
			plantilla.setEstado(input.getPlantilla().getEstado());
			
			plantilla = this.service.save(plantilla);
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			

			output.setPlantilla(this.mapearModelo(plantilla));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	private com.cycsystems.heymebackend.common.PlantillaNotificacion mapearModelo(PlantillaNotificacion entity) {
		com.cycsystems.heymebackend.common.PlantillaNotificacion modelo = new com.cycsystems.heymebackend.common.PlantillaNotificacion();
		modelo.setIdPlantillaNotificacion(entity.getIdPlantillaNotificacion());
		modelo.setTitulo(entity.getTitulo());
		modelo.setPlantilla(entity.getPlantilla());
		modelo.setEstado(entity.getEstado());
		modelo.setCanal(new Canal(entity.getCanal().getIdCanal(), entity.getCanal().getNombre()));
		return modelo;
	}
}
