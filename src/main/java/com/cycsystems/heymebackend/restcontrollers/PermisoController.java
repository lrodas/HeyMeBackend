package com.cycsystems.heymebackend.restcontrollers;

import java.util.ArrayList;
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

import com.cycsystems.heymebackend.common.Permiso;
import com.cycsystems.heymebackend.input.PermisoRequest;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.service.IPermisoService;
import com.cycsystems.heymebackend.output.PermisoResponse;
import com.cycsystems.heymebackend.util.Constants;

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
			output.setCodigo("0065");
			output.setDescripcion("Es necesario enviar el id del role");
			output.setIndicador("ERROR");
		} else {
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = this.permisoService.findByRole(input.getIdRole());
			
			output.setCodigo("0000");
			output.setDescripcion("Permisos obtenidos exitosamente");
			output.setIndicador("SUCCESS");
			output.setPermisos(this.mapPermisoEntity(permisos));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarPermisos(@RequestBody PermisoRequest input) {
		
		LOG.info("METHOD: guardarPermisos() --PARAMS: permisoRequest: " + input);
		PermisoResponse output = new PermisoResponse();
		boolean hayError = false;
		
		if (input.getPermisos() == null || input.getPermisos().isEmpty()) {
			output.setCodigo("0050");
			output.setDescripcion("Es necesario enviar algunos permisos");
			output.setIndicador("ERROR");
		} else {
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = new ArrayList<>();
			
			for (Permiso modelo: input.getPermisos()) {
				
				if (modelo.getOpcion() == null || 
						modelo.getOpcion().getIdOpcion() == null ||
						modelo.getOpcion().getIdOpcion() <= 0) {
					output.setCodigo("0051");
					output.setDescripcion("Es necesario enviar la opcion para asignar el permiso");
					output.setIndicador("ERROR");
					
					hayError = true;
					break;
				} else if (modelo.getPuesto() == null ||
						modelo.getPuesto().getIdRole() == null ||
						modelo.getPuesto().getIdRole() <= 0) {
					output.setCodigo("0052");
					output.setDescripcion("Es necesario enviar el role para asignar el permiso");
					output.setIndicador("ERROR");
					
					hayError = true;
					break;
				} else if (modelo.isAlta() == null || 
						modelo.isBaja() == null || modelo.isCambio() == null ||
						modelo.isImprimir() == null) {
					output.setCodigo("0053");
					output.setDescripcion("Es necesario indicar los permisos que tendra el role");
					output.setIndicador("ERROR");
					
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
				
				output.setCodigo("0000");
				output.setDescripcion("Permisos guardados exitosamente");
				output.setIndicador("SUCCESS");
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findByRole")
	public ListenableFuture<ResponseEntity<?>> obtenerPermisosPorRole(@RequestBody PermisoRequest input) {
		
		LOG.info("METHOD: obtenerPermisosPorRole() --PARAMS: ");
		PermisoResponse output = new PermisoResponse();
		
		if (input.getRole() == null || input.getRole().isEmpty()) {
			output.setCodigo("0054");
			output.setDescripcion("Es necesario ingresar el nombre del role");
			output.setIndicador("ERROR");
		} else {
			
			
			List<com.cycsystems.heymebackend.models.entity.Permiso> permisos = this.permisoService.findByRole(input.getRole());
			
			output.setCodigo("0000");
			output.setDescripcion("Permisos obtenidos exitosamente");
			output.setIndicador("SUCCESS");
			output.setPermisos(this.mapPermisoEntity(permisos));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private List<Permiso> mapPermisoEntity(List<com.cycsystems.heymebackend.models.entity.Permiso> permisos) {
		List<Permiso> modelos = new ArrayList<>();
		
		for(com.cycsystems.heymebackend.models.entity.Permiso permiso: permisos) {
			if (permiso.isAlta() || permiso.isBaja() || permiso.isCambio() || permiso.isImprimir()) {				
				Permiso modelo = new Permiso();
				
				modelo.setAlta(permiso.isAlta());
				modelo.setBaja(permiso.isBaja());
				modelo.setCambio(permiso.isCambio());
				modelo.setImprimir(permiso.isImprimir());
				modelo.setIdPermiso(permiso.getIdPermiso());
				
				com.cycsystems.heymebackend.common.Opcion opcion = new com.cycsystems.heymebackend.common.Opcion();
				opcion.setIdOpcion(permiso.getOpcion().getIdOpcion());
				opcion.setDescripcion(permiso.getOpcion().getDescripcion());
				opcion.setEvento(permiso.getOpcion().isEvento());
				opcion.setIcono(permiso.getOpcion().getIcono());
				opcion.setOrden(permiso.getOpcion().getOrden());
				opcion.setUrl(permiso.getOpcion().getUrl());
				modelo.setOpcion(opcion);
				
				com.cycsystems.heymebackend.common.Role role = new com.cycsystems.heymebackend.common.Role();
				role.setIdRole(permiso.getPuesto().getIdRole());
				role.setNombre(permiso.getPuesto().getNombre());
				role.setDescripcion(permiso.getPuesto().getDescripcion());
				role.setEstado(permiso.getPuesto().getEstado());
				modelo.setPuesto(role);
				
				modelos.add(modelo);				
			}
		}
		
		return modelos;
	}
}
