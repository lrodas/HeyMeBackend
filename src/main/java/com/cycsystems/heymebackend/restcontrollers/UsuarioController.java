package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.input.CambioContrasenaRequest;
import com.cycsystems.heymebackend.input.UsuarioRequest;
import com.cycsystems.heymebackend.models.entity.*;
import com.cycsystems.heymebackend.models.service.*;
import com.cycsystems.heymebackend.output.CambioContrasenaResponse;
import com.cycsystems.heymebackend.output.UsuarioResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;

import com.cycsystems.heymebackend.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/user")
public class UsuarioController {

	private Logger LOG = LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IEmpresaService empresaService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPermisoService permisoService;

	@Autowired
	private IOpcionService opcionService;

	@Autowired
	private IParametroService parametroService;

	// @Autowired
    // private MessageSource messageSource;

	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${status.user.lock}")
	private Integer STATUS_USER_LOCK;

	@Value("${status.user.active}")
	private Integer STATUS_USER_ACTIVE;
	
	@Async
	@PostMapping("/changeStatus")
	public ListenableFuture<ResponseEntity<?>> cambiarEstado(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: cambiarEstado() --PARAMS: input: " + input);
		UsuarioResponse output = new UsuarioResponse();
		
		
		if (input.getDatos() == null) {
			output.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			output.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else if (input.getDatos().getIdUsuario() == null || input.getDatos().getIdUsuario() <= 0) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getEnabled() == null) {
			output.setCodigo(Response.USER_STATUS_NOT_EMPTY_ERROR.getCodigo());
			output.setDescripcion(Response.USER_STATUS_NOT_EMPTY_ERROR.getMessage());
			output.setIndicador(Response.USER_STATUS_NOT_EMPTY_ERROR.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getDatos().getIdUsuario());
			usuario.setEnabled(input.getDatos().getEnabled());
						
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setUsuario(this.mapUsuario(this.usuarioService.save(usuario)));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveUsers")
	public ListenableFuture<ResponseEntity<?>> obtenerUsuarios(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: obtenerUsuario() --PARAMS: input: " + input);
		UsuarioResponse output = new UsuarioResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
		
		List<Usuario> usuarios = this.usuarioService.findAll(usuario.getEmpresa().getIdEmpresa());
		
		for (Usuario usuarioDB: usuarios) {
			output.getUsuarios().add(this.mapUsuario(usuarioDB));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveUserByUserName")
	public ListenableFuture<ResponseEntity<?>> obtenerUsuarioPorCorreo(@RequestBody UsuarioRequest input) {
	
		LOG.info("METHOD: obtenerUsuarioPorCorreo() --PARAMS: input: " + input);
		UsuarioResponse output = new UsuarioResponse();
		
		if (input.getDatos().getUsername() == null || input.getDatos().getUsername().isEmpty()) {
			output.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuariosDB = this.usuarioService.findByUsername(input.getDatos().getUsername());

			com.cycsystems.heymebackend.common.Usuario usuario = this.mapUsuario(usuariosDB);
			
			output.setUsuario(usuario);
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveUsersByName")
	public ListenableFuture<ResponseEntity<?>> obtenerUsuariosPorNombres(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: otenerUsuariosPorNombres() --PARAMS: usuarioRequest: " + input);
		UsuarioResponse output = new UsuarioResponse();
		
		if (input.getDatos() == null ||
				input.getDatos().getNombres() == null || input.getDatos().getNombres().isEmpty()) {
			output.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			List<Usuario> usuarios = this.usuarioService.findByName(usuario.getEmpresa().getIdEmpresa(), input.getDatos().getNombres());
			List<com.cycsystems.heymebackend.common.Usuario> modelos = new ArrayList<>();
			
			for (Usuario usuarioDB: usuarios) {
				modelos.add(this.mapUsuario(usuarioDB));
			}
			
			output.setUsuarios(modelos);
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveUsersByDate")
	public ListenableFuture<ResponseEntity<?>> obtenerUsuariosPorFechas(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: obtenerUsuariosPorFechas() --PARAMS: UsuarioRequest: " + input);
		UsuarioResponse output = new UsuarioResponse();
		Date fechaInicio = null;
		Date fechaFin = null;
		
		if (input.getFechaFin() == null) {
			output.setCodigo(Response.END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.END_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaInicio() == null) {
			output.setCodigo(Response.START_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.START_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.START_DATE_NOT_EMPTY.getIndicador());
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
		    Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			
			List<Usuario> usuarios = this.usuarioService.findByStartDate(usuario.getEmpresa().getIdEmpresa(), fechaInicio, fechaFin);
			List<com.cycsystems.heymebackend.common.Usuario> modelos = new ArrayList<>();
			
			for (Usuario usuarioDB: usuarios) {
				modelos.add(this.mapUsuario(usuarioDB));
			}
			
			output.setUsuarios(modelos);
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/resetPassword")
	public ListenableFuture<ResponseEntity<?>> restablecerContasena(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: restablecerContrasena() --PARAMS: usuarioRequest: " + input);
		UsuarioResponse response = new UsuarioResponse();
		
		if (input.getDatos().getUsername() == null || input.getDatos().getUsername().isEmpty()) {
			response.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {
			
			// mailService.sendMail(MAIL_FROM, input.getUsername(), subject, body);
		}
		
		return new AsyncResult<>(ResponseEntity.ok("")); 
	}
	
	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarUsuario(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: guardarUsuario() --PARAMS: usuario: " + input);
		UsuarioResponse response = new UsuarioResponse();
		
		if (input.getDatos().getNombres() == null || input.getDatos().getNombres().isEmpty()) {
			response.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getApellidos() == null || input.getDatos().getApellidos().isEmpty()) {
			response.setCodigo(Response.APELLIDO_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.APELLIDO_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.APELLIDO_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getDireccion() == null || input.getDatos().getDireccion().isEmpty()) {
			response.setCodigo(Response.DIRECCION_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.DIRECCION_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.DIRECCION_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getTelefono() == null || input.getDatos().getTelefono().isEmpty()) { 
			response.setCodigo(Response.TELEFONO_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.TELEFONO_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.TELEFONO_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getGenero() == null || input.getDatos().getGenero().getIdGenero() == null || input.getDatos().getGenero().getIdGenero() <= 0) {
			response.setCodigo(Response.GENERO_ERROR.getCodigo());
			response.setDescripcion(Response.GENERO_ERROR.getMessage());
			response.setIndicador(Response.GENERO_ERROR.getIndicador());
		} else if (input.getDatos().getUsername() == null || input.getDatos().getUsername().isEmpty()) {
			response.setCodigo(Response.CORREO_ERROR.getCodigo());
			response.setDescripcion(Response.CORREO_ERROR.getMessage());
			response.setIndicador(Response.CORREO_ERROR.getIndicador());
		} else if (input.getDatos().getPassword() == null || input.getDatos().getPassword().isEmpty()) {
			response.setCodigo(Response.PASSWORD_ERROR.getCodigo());
			response.setDescripcion(Response.PASSWORD_ERROR.getMessage());
			response.setIndicador(Response.PASSWORD_ERROR.getIndicador());
		} else if (input.getDatos().getEmpresa() == null) {
			response.setCodigo(Response.COMPANY_NOT_EMPTY_ERROR.getCodigo());
			response.setDescripcion(Response.COMPANY_NOT_EMPTY_ERROR.getMessage());
			response.setIndicador(Response.COMPANY_NOT_EMPTY_ERROR.getIndicador());
		} else {

			Usuario username = this.usuarioService.findByUsername(input.getDatos().getUsername());

			if (username != null) {
				response.setCodigo(Response.USER_NAME_EXIST.getCodigo());
				response.setDescripcion(Response.USER_NAME_EXIST.getMessage());
				response.setIndicador(Response.USER_NAME_EXIST.getIndicador());
			} else {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(input.getDatos().getIdUsuario());
				usuario.setNombres(input.getDatos().getNombres());
				usuario.setApellidos(input.getDatos().getApellidos());
				usuario.setTelefono(input.getDatos().getTelefono());
				usuario.setDireccion(input.getDatos().getDireccion());
				usuario.setGenero(new Genero(input.getDatos().getGenero().getIdGenero(), input.getDatos().getGenero().getDescripcion()));
				usuario.setUsername(input.getDatos().getUsername());
				usuario.setPassword(this.passwordEncoder.encode(input.getDatos().getPassword()));
				usuario.setImg(input.getDatos().getImg());
				usuario.setEnabled(input.getDatos().getEnabled());
				usuario.setEnabled(true);

				if (input.getDatos().getEmpresa().getCodigo() == null || input.getDatos().getEmpresa().getCodigo().isEmpty()) {
					String codigo = "";
					Boolean existe = false;
					do {
						codigo = UUID.randomUUID().toString();
						existe = this.empresaService.existsByCode(codigo);
					} while (existe == true);

					usuario.setEstadoUsuario(new EstadoUsuario(this.STATUS_USER_ACTIVE));

					Empresa empresa = new Empresa();
					empresa.setNombreEmpresa(input.getDatos().getEmpresa().getNombreEmpresa());
					empresa.setTelefono(input.getDatos().getEmpresa().getTelefono());
					empresa.setDireccion(input.getDatos().getEmpresa().getDireccion());
					empresa.setCodigo(codigo);
					empresa = this.empresaService.save(empresa);

					if (empresa != null && empresa.getIdEmpresa() != null && empresa.getIdEmpresa() > 0) {
						Role roleAdmin = new Role();
						roleAdmin.setDescripcion("ADMINISTRADOR");
						roleAdmin.setEstado(true);
						roleAdmin.setNombre("ROLE_ADMIN");
						roleAdmin.setEmpresa(empresa);

						roleAdmin = this.roleService.save(roleAdmin);

						if (roleAdmin != null && roleAdmin.getIdRole() != null && roleAdmin.getIdRole() > 0) {

							List<Opcion> opciones = this.opcionService.findAll();
							List<Permiso> permisos = new ArrayList<>();

							for (Opcion opcion: opciones) {
								Permiso permiso = new Permiso ();
								permiso.setAlta(true);
								permiso.setBaja(true);
								permiso.setCambio(true);
								permiso.setImprimir(true);
								permiso.setPuesto(roleAdmin);
								permiso.setOpcion(opcion);

								permisos.add(permiso);
							}

							this.permisoService.saveAll(permisos);
						}

						Role notRole = new Role();
						notRole.setEmpresa(empresa);
						notRole.setNombre("ROLE_SIN_ROLE");
						notRole.setEstado(true);
						notRole.setDescripcion("SIN PUESTO");

						this.roleService.save(notRole);

						List<Parametro> parametros = new ArrayList<>();

						// cuenta para envio de mailing
						Parametro mailParam = new Parametro();
						mailParam.setEmpresa(empresa);
						mailParam.setNombre("mail.from");
						mailParam.setValor(env.getProperty("parametro.mail"));
						parametros.add(mailParam);

						// sid cuenta twillio
						Parametro accountSid = new Parametro();
						accountSid.setEmpresa(empresa);
						accountSid.setNombre("twilio.account.sid");
						accountSid.setValor(env.getProperty("parametro.twilio.account.sid"));
						parametros.add(accountSid);

						// token twillio
						Parametro tokenParam = new Parametro();
						tokenParam.setNombre("twilio.account.auth.token");
						tokenParam.setValor(env.getProperty("parametro.twilio.account.auth.token"));
						tokenParam.setEmpresa(empresa);
						parametros.add(tokenParam);

						// Service twillio
						Parametro serviceParam = new Parametro();
						serviceParam.setEmpresa(empresa);
						serviceParam.setNombre("twilio.account.service.id");
						serviceParam.setValor(env.getProperty("parametro.twilio.account.service.id"));
						parametros.add(serviceParam);

						// Tarifa por mensaje
						Parametro tarifaParam = new Parametro();
						tarifaParam.setEmpresa(empresa);
						tarifaParam.setNombre("notificacion.tarifa");
						tarifaParam.setValor(env.getProperty("parametro.notificacion.tarifa"));
						parametros.add(tarifaParam);

						// Url para imagenes
						Parametro urlParam = new Parametro();
						urlParam.setEmpresa(empresa);
						urlParam.setNombre("images.url");
						urlParam.setValor(env.getProperty("parametro.images.url"));
						parametros.add(urlParam);

						this.parametroService.save(parametros);

						usuario.setEmpresa(empresa);
						usuario.setRole(roleAdmin);
						usuario = this.usuarioService.save(usuario);

						if (usuario != null && usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
							response.setUsuario(mapUsuario(usuario));
							response.getUsuario().setPassword(":-)");
							response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
							response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
							response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
						} else {
							this.empresaService.removeEmpresa(empresa);
							response.setCodigo(Response.USER_ERROR_REGISTER.getCodigo());
							response.setDescripcion(Response.USER_ERROR_REGISTER.getMessage());
							response.setIndicador(Response.USER_ERROR_REGISTER.getIndicador());
						}

					} else {
						response.setCodigo(Response.USER_ERROR_REGISTER.getCodigo());
						response.setDescripcion(Response.USER_ERROR_REGISTER.getMessage());
						response.setIndicador(Response.USER_ERROR_REGISTER.getIndicador());
					}
				} else if (this.empresaService.existsByCode(input.getDatos().getEmpresa().getCodigo().trim())) {
					Empresa empresa = this.empresaService.findByCode(input.getDatos().getEmpresa().getCodigo().trim());

					usuario.setEstadoUsuario(new EstadoUsuario(this.STATUS_USER_LOCK));
					usuario.setEmpresa(empresa);

					Role role = this.roleService.findByNombre(usuario.getEmpresa().getIdEmpresa(), "ROLE_SIN_ROLE");
					LOG.info("Role obtenido: " + role);
					usuario.setRole(role);
					usuario = this.usuarioService.save(usuario);

					response.setUsuario(mapUsuario(usuario));
					response.getUsuario().setPassword(":-)");
					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

				} else {
					response.setCodigo(Response.USER_ERROR_COMPANY_NOT_EXIST.getCodigo());
					response.setDescripcion(Response.USER_ERROR_COMPANY_NOT_EXIST.getCodigo());
					response.setIndicador(Response.USER_ERROR_COMPANY_NOT_EXIST.getIndicador());
				}
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
	
	@Async
	@PostMapping("/update")
	public ListenableFuture<ResponseEntity<?>> actualizarUsuario(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: guardarUsuario() --PARAMS: usuario: " + input);
		UsuarioResponse response = new UsuarioResponse();
		
		if (input.getDatos().getNombres() == null || input.getDatos().getNombres().isEmpty()) {
			response.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getApellidos() == null || input.getDatos().getApellidos().isEmpty()) {
			response.setCodigo(Response.APELLIDO_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.APELLIDO_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.APELLIDO_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getDireccion() == null || input.getDatos().getDireccion().isEmpty()) {
			response.setCodigo(Response.DIRECCION_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.DIRECCION_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.DIRECCION_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getTelefono() == null || input.getDatos().getTelefono().isEmpty()) { 
			response.setCodigo(Response.TELEFONO_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.TELEFONO_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.TELEFONO_USUARIO_ERROR.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getDatos().getIdUsuario());
			usuario.setNombres(input.getDatos().getNombres());
			usuario.setApellidos(input.getDatos().getApellidos());
			usuario.setTelefono(input.getDatos().getTelefono());
			usuario.setDireccion(input.getDatos().getDireccion());
			usuario.setRole(new Role(input.getDatos().getRole().getIdRole()));
			
			usuario = this.usuarioService.save(usuario);
			
			response.setUsuario(mapUsuario(usuario));
			
			response.getUsuario().setPassword(":-)");
			
			response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			
		}
				
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
	
	@Async
	@PostMapping("/changePassword")
	public AsyncResult<ResponseEntity<?>> cambiarContrasena(@RequestBody CambioContrasenaRequest request) {
		
		LOG.info("METHOD: cambiarContrasena() --PARAMS: CambioContrasenaRequest: " + request);
		CambioContrasenaResponse response = new CambioContrasenaResponse();
		
		if (request.getContrasenaActual() == null || request.getContrasenaActual().isEmpty()) {
			response.setCodigo(Response.PASSWORD_ERROR.getCodigo());
			response.setDescripcion(Response.PASSWORD_ERROR.getMessage());
			response.setIndicador(Response.PASSWORD_ERROR.getIndicador());
		} else if (request.getNuevaContrasena() == null || request.getNuevaContrasena().isEmpty()) {
			response.setCodigo(Response.PASSWORD_VALIDATION.getCodigo());
			response.setDescripcion(Response.PASSWORD_VALIDATION.getMessage());
			response.setIndicador(Response.PASSWORD_VALIDATION.getIndicador());
		} else if (request.getIdUsuario() == null || request.getIdUsuario() <= 0) {
			response.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {
			
			Usuario usuario = this.usuarioService.findById(request.getIdUsuario());
			
			if (usuario != null) {
				
				if (this.passwordEncoder.matches(request.getContrasenaActual(), usuario.getPassword()) ) {
					
					usuario.setPassword(this.passwordEncoder.encode(request.getNuevaContrasena()));
					this.usuarioService.save(usuario);
					
					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				} else {
					response.setCodigo(Response.PASSWORD_DONT_MATCH_ERROR.getCodigo());
					response.setDescripcion(Response.PASSWORD_DONT_MATCH_ERROR.getMessage());
					response.setIndicador(Response.PASSWORD_DONT_MATCH_ERROR.getIndicador());
				}
			} else {
				response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
	
	private com.cycsystems.heymebackend.common.Usuario mapUsuario(Usuario entityUsuario) {
		
		com.cycsystems.heymebackend.common.Usuario usuario = new com.cycsystems.heymebackend.common.Usuario();
		usuario.setIdUsuario(entityUsuario.getIdUsuario());
		usuario.setNombres(entityUsuario.getNombres());
		usuario.setApellidos(entityUsuario.getApellidos());
		usuario.setDireccion(entityUsuario.getDireccion());
		usuario.setEnabled(entityUsuario.getEnabled());
		usuario.setGenero(new com.cycsystems.heymebackend.common.Genero(entityUsuario.getGenero().getIdGenero(), entityUsuario.getGenero().getDescripcion()));
		usuario.setImg(entityUsuario.getImg());
		usuario.setRole(new com.cycsystems.heymebackend.common.Role(
				entityUsuario.getRole().getIdRole(),
				entityUsuario.getRole().getNombre(),
				entityUsuario.getRole().getDescripcion(),
				entityUsuario.getRole().getEstado()));
		usuario.setTelefono(entityUsuario.getTelefono());
		usuario.setUsername(entityUsuario.getUsername());
		usuario.setEmpresa(this.mapEmpresaModelo(entityUsuario.getEmpresa()));
		
		return usuario;
	}

	private Empresa mapEmpresa (com.cycsystems.heymebackend.common.Empresa modelo) {
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(modelo.getIdEmpresa());
		empresa.setNombreEmpresa(modelo.getNombreEmpresa());
		empresa.setDireccion(modelo.getDireccion());
		empresa.setTelefono(modelo.getTelefono());
		empresa.setCodigo(modelo.getCodigo());

		return empresa;
	}

	private com.cycsystems.heymebackend.common.Empresa mapEmpresaModelo(Empresa empresa) {
		com.cycsystems.heymebackend.common.Empresa modelo = new com.cycsystems.heymebackend.common.Empresa();
		modelo.setIdEmpresa(empresa.getIdEmpresa());
		modelo.setNombreEmpresa(empresa.getNombreEmpresa());
		modelo.setDireccion(empresa.getDireccion());
		modelo.setTelefono(empresa.getTelefono());
		modelo.setCodigo(empresa.getCodigo());

		return modelo;
	}
}
