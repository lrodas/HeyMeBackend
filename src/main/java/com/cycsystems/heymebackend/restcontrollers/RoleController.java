package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Opcion;
import com.cycsystems.heymebackend.input.RoleRequest;
import com.cycsystems.heymebackend.models.entity.Permiso;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IRoleService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.RoleResponse;
import com.cycsystems.heymebackend.util.Constants;
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

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/role")
public class RoleController {

	private Logger LOG = LogManager.getLogger(RoleController.class);
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Async
	@PostMapping("/changeStatus")
	public ListenableFuture<ResponseEntity<?>> cambiarEstado(@RequestBody RoleRequest input) {

		LOG.info("METHOD: cambiarEstado() --PARAMS: RoleRequest: " + input);
		RoleResponse output = new RoleResponse();

		if (input.getRole().getIdRole() == null || input.getRole().getIdRole() <= 0) {
			output.setCodigo("0065");
			output.setDescripcion("Es necesario enviar el id del role");
			output.setIndicador("ERROR");
		} else if (input.getRole().getEstado() == null) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el estado del role");
			output.setIndicador("ERROR");
		} else {

			Role role = this.roleService.findById(input.getRole().getIdRole());

			if (role != null) {
				role.setEstado(input.getRole().getEstado());
				role = this.roleService.save(role);

				output.setCodigo("0000");
				output.setDescripcion("Role guardado exitosamente");
				output.setIndicador("SUCCESS");
				output.setRole(this.mapRole(role));
			} else {
				output.setCodigo("0000");
				output.setDescripcion("El role no existe, por  favor verificar");
				output.setIndicador("ERROR");
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
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
			role.setPermisos(this.mapPermisos(input.getRole().getPermisos()));
			
			
			role = this.roleService.save(role);
			
			output.setCodigo("0000");
			output.setDescripcion("Role guardado exitosamente");
			output.setIndicador("SUCCESS");
			output.setRole(this.mapRole(role));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerTodos() --PARAMS: roleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			
			output.setCodigo("0062");
			output.setDescripcion("Debe enviar el id del usuario a cambiar el estado");
			output.setIndicador("ERROR");
		} else {
			
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			List<Role> roles = this.roleService.findAll(usuario.getEmpresa().getIdEmpresa());
			for (Role role: roles) {
				output.getRoles().add(this.mapRole(role));
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Roles obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorEstado(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerRolesPorEstado() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getEstado() == null) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el estado del role");
			output.setIndicador("ERROR");
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			
			output.setCodigo("0062");
			output.setDescripcion("Debe enviar el id del usuario a cambiar el estado");
			output.setIndicador("ERROR");
			
		} else {
			
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			
			List<Role> roles = this.roleService.findByStatus(usuario.getEmpresa().getIdEmpresa(), input.getRole().getEstado());
			
			for (Role role: roles) {
				output.getRoles().add(this.mapRole(role));
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Roles obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByName")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorNombre(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerRolesPorNombre() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getDescripcion() == null || input.getRole().getDescripcion().isEmpty()) {
			output.setCodigo("0049");
			output.setDescripcion("Es necesario enviar el nombre del role");
			output.setIndicador("ERROR");
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			
			output.setCodigo("0062");
			output.setDescripcion("Debe enviar el id del usuario a cambiar el estado");
			output.setIndicador("ERROR");
			
		} else {
			
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			List<Role> roles = this.roleService.findByNombreLike(usuario.getEmpresa().getIdEmpresa(), input.getRole().getDescripcion());
			
			for (Role role: roles) {
				output.getRoles().add(this.mapRole(role));
			}
			
			output.setCodigo("0000");
			output.setDescripcion("Roles obtenidos exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> obtenerRolePorId(@RequestBody RoleRequest input) {
		
		LOG.info("METHOD: obtenerRolePorId() --PARAMS: roleRequest: " + input);
		RoleResponse output = new RoleResponse();
		
		if (input.getRole().getIdRole() == null || input.getRole().getIdRole() <= 0) {
			output.setCodigo("0065");
			output.setDescripcion("Es necesario enviar el id del role");
			output.setIndicador("ERROR");
		} else {
			
			Role role = this.roleService.findById(input.getRole().getIdRole());
			
			output.setRole(this.mapRole(role));
			
			output.setCodigo("0000");
			output.setDescripcion("Role obtenido exitosamente");
			output.setIndicador("SUCCESS");
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private com.cycsystems.heymebackend.common.Role mapRole(Role role) {
		
		com.cycsystems.heymebackend.common.Role modelo = new com.cycsystems.heymebackend.common.Role();
		modelo.setIdRole(role.getIdRole());
		modelo.setNombre(role.getNombre());
		modelo.setDescripcion(role.getDescripcion());
		modelo.setEstado(role.getEstado());
		
		for (Permiso permiso: role.getPermisos()) {
			com.cycsystems.heymebackend.common.Permiso permisoModelo = new com.cycsystems.heymebackend.common.Permiso();
			permisoModelo.setAlta(permiso.isAlta());
			permisoModelo.setBaja(permiso.isBaja());
			permisoModelo.setCambio(permiso.isCambio());
			permisoModelo.setImprimir(permiso.isImprimir());
			permisoModelo.setIdPermiso(permiso.getIdPermiso());
			
			Opcion opcion = new Opcion();
			opcion.setIdOpcion(permiso.getOpcion().getIdOpcion());
			opcion.setDescripcion(permiso.getOpcion().getDescripcion());
			opcion.setEvento(permiso.getOpcion().isEvento());
			opcion.setIcono(permiso.getOpcion().getIcono());
			opcion.setOrden(permiso.getOpcion().getOrden());
			opcion.setUrl(permiso.getOpcion().getUrl());
			
			permisoModelo.setOpcion(opcion);
			modelo.getPermisos().add(permisoModelo);
			
		}
		
		return modelo;
	}
	
	private List<Permiso> mapPermisos(List<com.cycsystems.heymebackend.common.Permiso> modelos ) {
		List<Permiso> permisos = new ArrayList<>();
		for (com.cycsystems.heymebackend.common.Permiso modelo: modelos) {
			
			Permiso permiso = new Permiso();
			permiso.setAlta(modelo.isAlta());
			permiso.setBaja(modelo.isBaja());
			permiso.setCambio(modelo.isCambio());
			permiso.setImprimir(modelo.isImprimir());
			permiso.setIdPermiso(modelo.getIdPermiso());
			
			com.cycsystems.heymebackend.models.entity.Opcion opcion = new com.cycsystems.heymebackend.models.entity.Opcion();
			opcion.setIdOpcion(modelo.getOpcion().getIdOpcion());
			opcion.setDescripcion(modelo.getOpcion().getDescripcion());
			opcion.setIcono(modelo.getOpcion().getIcono());
			opcion.setOrden(modelo.getOpcion().getOrden());
			opcion.setUrl(modelo.getOpcion().getUrl());
			permiso.setOpcion(opcion);
			
			permisos.add(permiso);
		}
		return permisos;
	}
}
