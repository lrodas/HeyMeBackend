package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Permiso;
import com.cycsystems.heymebackend.convert.CPermiso;
import com.cycsystems.heymebackend.convert.CRole;
import com.cycsystems.heymebackend.input.RoleRequest;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IRoleService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.RoleResponse;
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

import java.util.List;
import java.util.stream.Collectors;

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
			output.setCodigo(Response.ROLE_ID_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_ID_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_ID_NOT_EMPTY.getIndicador());
		} else if (input.getRole().getEstado() == null) {
			output.setCodigo(Response.ROLE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_STATUS_NOT_EMPTY.getIndicador());
		} else {

			Role role = this.roleService.findById(input.getRole().getIdRole());

			if (role != null) {
				role.setEstado(input.getRole().getEstado());
				role = this.roleService.save(role);

				com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
				model.setPermisos(role.getPermisos()
					.stream()
					.map(CPermiso::EntityToModel)
					.collect(Collectors.toList()));

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setRole(model);
			} else {
				output.setCodigo(Response.ROLE_NOT_EXIST.getCodigo());
				output.setDescripcion(Response.ROLE_NOT_EXIST.getMessage());
				output.setIndicador(Response.ROLE_NOT_EXIST.getIndicador());
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
			output.setCodigo(Response.ROLE_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getRole().getDescripcion() == null || input.getRole().getDescripcion().isEmpty()) {
			output.setCodigo(Response.ROLE_DESCRIPTION_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_DESCRIPTION_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_DESCRIPTION_NOT_EMPTY.getIndicador());
		} else if (input.getRole().getEstado() == null) {
			output.setCodigo(Response.ROLE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_STATUS_NOT_EMPTY.getIndicador());
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Role role = new Role();

				if (input.getRole().getIdRole() != null && input.getRole().getIdRole() > 0) {
					role = this.roleService.findById(input.getRole().getIdRole());
				}

				role.setNombre(input.getRole().getNombre());
				role.setDescripcion(input.getRole().getDescripcion());
				role.setEstado(input.getRole().getEstado());
				role.setEmpresa(usuario.getEmpresa());
				role.setPermisos(input.getRole().getPermisos()
					.stream()
					.map(CPermiso::ModelToEntity)
					.collect(Collectors.toList()));

				role = this.roleService.save(role);
				com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
				model.setPermisos(role.getPermisos()
						.stream()
						.map(CPermiso::EntityToModel)
						.collect(Collectors.toList()));

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setRole(model);
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos(@RequestBody RoleRequest input) {

		LOG.info("METHOD: obtenerTodos() --PARAMS: roleRequest: " + input);
		RoleResponse output = new RoleResponse();

		if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {

			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<Role> roles = this.roleService.findAll(usuario.getEmpresa().getIdEmpresa());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setRoles(roles
						.stream()
						.map(role -> {
							com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
							List<com.cycsystems.heymebackend.common.Permiso> permisos = role.getPermisos()
									.stream()
									.map(CPermiso::EntityToModel)
									.collect(Collectors.toList());
							model.setPermisos(permisos);
							return model;
						})
						.collect(Collectors.toList()));
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorEstado(@RequestBody RoleRequest input) {

		LOG.info("METHOD: obtenerRolesPorEstado() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();

		if (input.getRole().getEstado() == null) {
			output.setCodigo(Response.ROLE_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_STATUS_NOT_EMPTY.getIndicador());
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<Role> roles = this.roleService.findByStatus(usuario.getEmpresa().getIdEmpresa(), input.getRole().getEstado());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setRoles(roles
						.stream()
						.map(role -> {
							com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
							List<com.cycsystems.heymebackend.common.Permiso> permisos = role.getPermisos()
									.stream()
									.map(CPermiso::EntityToModel)
									.collect(Collectors.toList());
							model.setPermisos(permisos);
							return model;
						})
						.collect(Collectors.toList()));
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findByName")
	public ListenableFuture<ResponseEntity<?>> obtenerRolesPorNombre(@RequestBody RoleRequest input) {

		LOG.info("METHOD: obtenerRolesPorNombre() --PARAMS RoleRequest: " + input);
		RoleResponse output = new RoleResponse();

		if (input.getRole().getDescripcion() == null || input.getRole().getDescripcion().isEmpty()) {
			output.setCodigo(Response.ROLE_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<Role> roles = this.roleService.findByNombreLike(usuario.getEmpresa().getIdEmpresa(), input.getRole().getDescripcion());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setRoles(roles
						.stream()
						.map(role -> {
							com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
							List<com.cycsystems.heymebackend.common.Permiso> permisos = role.getPermisos()
									.stream()
									.map(CPermiso::EntityToModel)
									.collect(Collectors.toList());
							model.setPermisos(permisos);
							return model;
						})
						.collect(Collectors.toList()));
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> obtenerRolePorId(@RequestBody RoleRequest input) {

		LOG.info("METHOD: obtenerRolePorId() --PARAMS: roleRequest: " + input);
		RoleResponse output = new RoleResponse();

		if (input.getRole().getIdRole() == null || input.getRole().getIdRole() <= 0) {
			output.setCodigo(Response.ROLE_ID_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.ROLE_ID_NOT_EMPTY.getMessage());
			output.setIndicador(Response.ROLE_ID_NOT_EMPTY.getIndicador());
		} else {

			Role role = this.roleService.findById(input.getRole().getIdRole());
			com.cycsystems.heymebackend.common.Role model = CRole.EntityToModel(role);
			List<Permiso> permisos = role
					.getPermisos()
					.stream()
					.map(CPermiso::EntityToModel)
					.collect(Collectors.toList());
			model.setPermisos(permisos);
			output.setRole(model);

			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
}
