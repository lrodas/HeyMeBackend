package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Permiso;
import com.cycsystems.heymebackend.convert.CPermiso;
import com.cycsystems.heymebackend.input.PermisoRequest;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.service.IPermisoService;
import com.cycsystems.heymebackend.output.PermisoResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/permission")
public class PermisoController {

	private Logger LOG = LogManager.getLogger(PermisoController.class);
	
	@Autowired
	private IPermisoService permisoService;
	
	@Async
	@PostMapping("/retrievePermissionsByRole")
	public ListenableFuture<ResponseEntity<?>> obtenerPermisosPorId(@RequestBody PermisoRequest input) {
		
		LOG.info("METHOD: obtenerPermisosPorId() --permisoRequest: " + input);
		PermisoResponse output = new PermisoResponse();
		
		if (input.getIdRole() == null || input.getIdRole() <= 0) {
			output.setCodigo(Response.ROLE_ID_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_ID_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_ID_NOT_EMPTY.getIndicador());
		} else {
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = this.permisoService.findByRole(input.getIdRole());
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setPermisos(permisos
				.stream()
				.map(CPermiso::EntityToModel)
				.collect(Collectors.toList()));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarPermisos(@RequestBody PermisoRequest input) {
		
		LOG.info("METHOD: guardarPermisos() --PARAMS: permisoRequest: " + input);
		PermisoResponse output = new PermisoResponse();
		boolean hayError = false;
		
		if (input.getPermisos() == null || input.getPermisos().isEmpty()) {
			output.setCodigo(Response.PERMISSION_LIST_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.PERMISSION_LIST_NOT_EMPTY.getMessage());
			output.setIndicador(Response.PERMISSION_LIST_NOT_EMPTY.getIndicador());
		} else {
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = new ArrayList<>();
			
			for (Permiso modelo: input.getPermisos()) {
				
				if (modelo.getOpcion() == null || 
						modelo.getOpcion().getIdOpcion() == null ||
						modelo.getOpcion().getIdOpcion() <= 0) {
					output.setCodigo(Response.OPCION_NOT_EMPTY.getCodigo());
					output.setDescripcion(Response.OPCION_NOT_EMPTY.getMessage());
					output.setIndicador(Response.OPCION_NOT_EMPTY.getIndicador());
					
					hayError = true;
					break;
				} else if (modelo.getPuesto() == null ||
						modelo.getPuesto().getIdRole() == null ||
						modelo.getPuesto().getIdRole() <= 0) {
					output.setCodigo(Response.ROLE_NOT_EMPTY.getCodigo());
					output.setDescripcion(Response.ROLE_NOT_EMPTY.getMessage());
					output.setIndicador(Response.ROLE_NOT_EMPTY.getIndicador());
					
					hayError = true;
					break;
				} else if (modelo.isAlta() == null || 
						modelo.isBaja() == null || modelo.isCambio() == null ||
						modelo.isImprimir() == null) {
					output.setCodigo(Response.PERMISSION_TYPE_ACCESS.getCodigo());
					output.setDescripcion(Response.PERMISSION_TYPE_ACCESS.getMessage());
					output.setIndicador(Response.PERMISSION_TYPE_ACCESS.getIndicador());
					
					hayError = true;
					break;
				} else {
					
					com.cycsystems.heymebackend.models.entity.Permiso permiso = new com.cycsystems.heymebackend.models.entity.Permiso();
					
					permiso.setIdPermiso(modelo.getIdPermiso());
					permiso.setOpcion(new Opcion(modelo.getOpcion().getIdOpcion()));
					permiso.setPuesto(new Role(modelo.getPuesto().getIdRole()));
					permiso.setAlta(modelo.isAlta());
					permiso.setBaja(modelo.isBaja());
					permiso.setCambio(modelo.isCambio());
					permiso.setImprimir(modelo.isImprimir());

					permisos.add(permiso);
					
				}
			}
			
			if(!hayError ) {
				this.permisoService.saveAll(permisos);
				
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findByRole")
	public ListenableFuture<ResponseEntity<?>> obtenerPermisosPorRole(@RequestBody PermisoRequest input) {
		
		LOG.info("METHOD: obtenerPermisosPorRole() --PARAMS: " + input);
		PermisoResponse output = new PermisoResponse();
		
		if (input.getRole() == null || input.getRole().isEmpty()) {
			output.setCodigo(Response.ROLE_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_NAME_NOT_EMPTY.getIndicador());
		} else {
			
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = this.permisoService.findByRole(input.getRole());
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setPermisos(permisos
				.stream()
				.map(CPermiso::EntityToModel)
				.collect(Collectors.toList()));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	

}
