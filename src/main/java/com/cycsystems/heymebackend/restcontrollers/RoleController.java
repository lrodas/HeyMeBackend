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

import com.cycsystems.heymebackend.input.RoleRequest;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.service.IRoleService;
import com.cycsystems.heymebackend.output.RoleResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/role")
public class RoleController {

	private Logger LOG = LogManager.getLogger(RoleController.class);
	
	@Autowired
	private IRoleService roleService;
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarRole(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: guardarRole() --PARAMS: RoleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getNombre() == null || input.getRole().getNombre().isEmpty()) {
			output.setCodigo("0047");
			output.setDescripcion("Es necesario enviar el nombre del role");
			output.setIndicador("ERROR");
		} else if (input.getRole().getDescripcion() == null || input.getRole().getDescripcion().isEmpty()) {
			output.setCodigo("0048");
			output.setDescripcion("Es necesario enviar la descripcion del role");
			output.setIndicador("ERROR");
		} else if (input.getRole().getEstado() == null) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el estado del role");
			output.setIndicador("ERROR");
		} else {
			
			Role role = new Role();
			role.setIdRole(input.getRole().getIdRole());
			role.setNombre(input.getRole().getNombre());
			role.setDescripcion(input.getRole().getDescripcion());
			role.setEstado(input.getRole().getEstado());
			
			this.roleService.save(role);
			
			output.setCodigo("0000");
			output.setDescripcion("Role guardado exitosamente");
			output.setIndicador("SUCCESS");
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos() {
		
		LOG.info("METHOD: obtenerTodos()");
		
		List<Role> roles = this.roleService.findAll();
		RoleResponse output = new RoleResponse();
		
		for (Role role: roles) {
			com.cycsystems.heymebackend.common.Role model = new com.cycsystems.heymebackend.common.Role();
			
			model.setIdRole(role.getIdRole());
			model.setNombre(role.getNombre());
			model.setDescripcion(role.getDescripcion());
			model.setEstado(role.getEstado());
			
			output.getRoles().add(model);
		}
		
		output.setCodigo("0000");
		output.setDescripcion("Roles obtenidos exitosamente");
		output.setIndicador("SUCCESS");
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorEstado(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerRolesPorEstado() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getEstado() == null) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el estado del role");
			output.setIndicador("ERROR");
		} else {
			
			List<Role> roles = this.roleService.findByStatus(input.getRole().getEstado());
			
			for (Role role: roles) {
				com.cycsystems.heymebackend.common.Role modelo = new com.cycsystems.heymebackend.common.Role();
				
				modelo.setIdRole(role.getIdRole());
				modelo.setNombre(role.getNombre());
				modelo.setDescripcion(role.getDescripcion());
				modelo.setEstado(role.getEstado());
				output.getRoles().add(modelo);
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Roles obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@PostMapping("/findByTitle")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorTitulo(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerRolesPorTitulo() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getDescripcion() == null || input.getRole().getDescripcion().isEmpty()) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el titulo del role");
			output.setIndicador("ERROR");
		} else {
			
			List<Role> roles = this.roleService.findByTitle(input.getRole().getDescripcion());
			
			for (Role role: roles) {
				com.cycsystems.heymebackend.common.Role modelo = new com.cycsystems.heymebackend.common.Role();
				
				modelo.setIdRole(role.getIdRole());
				modelo.setNombre(role.getNombre());
				modelo.setDescripcion(role.getDescripcion());
				modelo.setEstado(role.getEstado());
				output.getRoles().add(modelo);
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Roles obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
}
