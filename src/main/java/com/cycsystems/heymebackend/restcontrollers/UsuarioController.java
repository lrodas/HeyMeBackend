package com.cycsystems.heymebackend.restcontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

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

import com.cycsystems.heymebackend.awsServices.AWSIamUser;
import com.cycsystems.heymebackend.convert.CUsuario;
import com.cycsystems.heymebackend.input.CambioContrasenaRequest;
import com.cycsystems.heymebackend.input.UsuarioRequest;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.EstadoUsuario;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.entity.Parametro;
import com.cycsystems.heymebackend.models.entity.Permiso;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.ICaptchaService;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.IFileStorageService;
import com.cycsystems.heymebackend.models.service.IOpcionService;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.IPermisoService;
import com.cycsystems.heymebackend.models.service.IRoleService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.output.CambioContrasenaResponse;
import com.cycsystems.heymebackend.output.UsuarioResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
import com.cycsystems.heymebackend.util.Util;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/user")
public class UsuarioController {

	private Logger LOG = LogManager.getLogger(UsuarioController.class);

	private final IUsuarioService usuarioService;

	private final IEmpresaService empresaService;

	private final IRoleService roleService;

	private final IPermisoService permisoService;

	private final IOpcionService opcionService;

	private final IParametroService parametroService;

	private final ICaptchaService captchaService;

	// @Autowired
	// private MessageSource messageSource;

	private final Environment env;

	private final BCryptPasswordEncoder passwordEncoder;

	private final MailServiceImpl mailService;

	@Value("${status.user.inactive}")
	private Integer STATUS_USER_INACTIVE;

	@Value("${status.user.active}")
	private Integer STATUS_USER_ACTIVE;

	@Value("${spring.mail.from.email}")
	private String MAIL_FROM;

	@Value("${subject.mail.activate.user}")
	private String SUBJECT_MAIL;

	@Value("${template.welcome}")
	private String MAIL_TEMPLATE_CONFIRM;

	@Autowired
	public UsuarioController(IUsuarioService usuarioService, IEmpresaService empresaService, IRoleService roleService,
			IPermisoService permisoService, IOpcionService opcionService, IParametroService parametroService,
			ICaptchaService captchaService, Environment env, BCryptPasswordEncoder passwordEncoder,
			MailServiceImpl mailService, IFileStorageService fileStorageService, AWSIamUser iamUser) {
		this.usuarioService = usuarioService;
		this.empresaService = empresaService;
		this.roleService = roleService;
		this.permisoService = permisoService;
		this.opcionService = opcionService;
		this.parametroService = parametroService;
		this.captchaService = captchaService;
		this.env = env;
		this.passwordEncoder = passwordEncoder;
		this.mailService = mailService;
	}

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
		} else if (input.getDatos().getEstadoUsuario() == null
				&& input.getDatos().getEstadoUsuario().getIdEstadoUsuario() == null
				&& input.getDatos().getEstadoUsuario().getIdEstadoUsuario() <= 0) {
			output.setCodigo(Response.USER_STATUS_NOT_EMPTY_ERROR.getCodigo());
			output.setDescripcion(Response.USER_STATUS_NOT_EMPTY_ERROR.getMessage());
			output.setIndicador(Response.USER_STATUS_NOT_EMPTY_ERROR.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getDatos().getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				usuario.setEstadoUsuario(new EstadoUsuario(input.getDatos().getEstadoUsuario().getIdEstadoUsuario()));
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setUsuario(CUsuario.EntityToModel(this.usuarioService.save(usuario)));
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveUsers")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> obtenerUsuarios(@RequestBody UsuarioRequest input) {

		LOG.info("METHOD: obtenerUsuario() --PARAMS: input: " + input);
		UsuarioResponse output = new UsuarioResponse();
		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

		if (usuario == null) {
			output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
			output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
			output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
		} else {
			List<com.cycsystems.heymebackend.common.Usuario> usuarios = this.usuarioService
					.findAll(usuario.getEmpresa().getIdEmpresa()).stream().map(CUsuario::EntityToModel)
					.collect(Collectors.toList());

			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setUsuarios(usuarios);

		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveUserByUserName")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> obtenerUsuarioPorCorreo(
			@RequestBody UsuarioRequest input) {

		LOG.info("METHOD: obtenerUsuarioPorCorreo() --PARAMS: input: " + input);
		UsuarioResponse output = new UsuarioResponse();

		if (input.getDatos().getUsername() == null || input.getDatos().getUsername().isEmpty()) {
			output.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findByUsername(input.getDatos().getUsername());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				output.setUsuario(CUsuario.EntityToModel(usuario));
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveUsersByName")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> obtenerUsuariosPorNombres(
			@RequestBody UsuarioRequest input) {

		LOG.info("METHOD: otenerUsuariosPorNombres() --PARAMS: usuarioRequest: " + input);
		UsuarioResponse output = new UsuarioResponse();

		if (input.getDatos() == null || input.getDatos().getNombres() == null
				|| input.getDatos().getNombres().isEmpty()) {
			output.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<com.cycsystems.heymebackend.common.Usuario> usuarios = this.usuarioService
						.findByName(usuario.getEmpresa().getIdEmpresa(), input.getDatos().getNombres()).stream()
						.filter(usuario1 -> usuario1.getEmpresa().getIdEmpresa()
								.compareTo(usuario.getEmpresa().getIdEmpresa()) == 0)
						.map(CUsuario::EntityToModel).collect(Collectors.toList());

				output.setUsuarios(usuarios);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveUsersByDate")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> obtenerUsuariosPorFechas(
			@RequestBody UsuarioRequest input) {

		LOG.info("METHOD: obtenerUsuariosPorFechas() --PARAMS: UsuarioRequest: " + input);
		UsuarioResponse output = new UsuarioResponse();

		if (input.getFechaFin() == null) {
			output.setCodigo(Response.END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.END_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaInicio() == null) {
			output.setCodigo(Response.START_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.START_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.START_DATE_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				Date fechaInicio = Util.mapDate(input.getFechaInicio());
				Date fechaFin = Util.mapDate(input.getFechaFin());

				List<com.cycsystems.heymebackend.common.Usuario> usuarios = this.usuarioService
						.findByStartDate(usuario.getEmpresa().getIdEmpresa(), fechaInicio, fechaFin).stream()
						.map(CUsuario::EntityToModel).collect(Collectors.toList());

				output.setUsuarios(usuarios);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/resetPassword")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> restablecerContasena(@RequestBody UsuarioRequest input) {

		LOG.info("METHOD: restablecerContrasena() --PARAMS: usuarioRequest: " + input);
		UsuarioResponse response = new UsuarioResponse();

		if (input.getDatos().getUsername() == null || input.getDatos().getUsername().isEmpty()) {
			response.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else {

			// mailService.sendMail(MAIL_FROM, input.getUsername(), subject, body);
		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> guardarUsuario(@RequestBody UsuarioRequest input)
			throws IOException {

		LOG.info("METHOD: guardarUsuario() --PARAMS: usuario: " + input);
		UsuarioResponse response = new UsuarioResponse();

		if (input.getDatos() == null) {
			response.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			response.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else if (input.getRecaptchaResponse() == null || input.getRecaptchaResponse().isEmpty()) {
			response.setCodigo(Response.RECAPTCHA_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.RECAPTCHA_NOT_EMPTY.getMessage());
			response.setIndicador(Response.RECAPTCHA_NOT_EMPTY.getIndicador());
		} else if (!this.captchaService.verify(input.getRecaptchaResponse())) {
			response.setCodigo(Response.RECAPTCHA_NOT_VALID.getCodigo());
			response.setDescripcion(Response.RECAPTCHA_NOT_VALID.getMessage());
			response.setIndicador(Response.RECAPTCHA_NOT_VALID.getIndicador());
		} else if (input.getDatos().getNombres() == null || input.getDatos().getNombres().isEmpty()) {
			response.setCodigo(Response.NOMBRE_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.NOMBRE_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.NOMBRE_USUARIO_ERROR.getIndicador());
		} else if (input.getDatos().getApellidos() == null || input.getDatos().getApellidos().isEmpty()) {
			response.setCodigo(Response.APELLIDO_USUARIO_ERROR.getCodigo());
			response.setDescripcion(Response.APELLIDO_USUARIO_ERROR.getMessage());
			response.setIndicador(Response.APELLIDO_USUARIO_ERROR.getIndicador());
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
				usuario.setUsername(input.getDatos().getUsername());
				usuario.setPassword(this.passwordEncoder.encode(input.getDatos().getPassword()));
				usuario.setEnabled(false);

				if (input.getDatos().getEmpresa().getCodigo() == null
						|| input.getDatos().getEmpresa().getCodigo().isEmpty()) {
					String codigo;
					boolean existe;
					do {
						codigo = UUID.randomUUID().toString();
						existe = this.empresaService.existsByCode(codigo);
					} while (existe);

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

							for (Opcion opcion : opciones) {
								Permiso permiso = new Permiso();
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
						mailParam.setValor(empresa.getNombreEmpresa().toLowerCase().replaceAll("[^a-zA-Z0-9]+", "")
								+ empresa.getIdEmpresa() + "@heyme.com.gt");
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
							response.setUsuario(CUsuario.EntityToModel(usuario));
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

					usuario.setEstadoUsuario(new EstadoUsuario(this.STATUS_USER_INACTIVE));
					usuario.setEmpresa(empresa);

					Role role = this.roleService.findByNombre(usuario.getEmpresa().getIdEmpresa(), "ROLE_SIN_ROLE");
					usuario.setRole(role);
					usuario = this.usuarioService.save(usuario);

					response.setUsuario(CUsuario.EntityToModel(usuario));
					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

				} else {
					response.setCodigo(Response.USER_ERROR_COMPANY_NOT_EXIST.getCodigo());
					response.setDescripcion(Response.USER_ERROR_COMPANY_NOT_EXIST.getMessage());
					response.setIndicador(Response.USER_ERROR_COMPANY_NOT_EXIST.getIndicador());
				}
			}

			if (response.getUsuario() != null && response.getUsuario().getIdUsuario() != null
					&& response.getUsuario().getIdUsuario().compareTo(0) > 0) {
				String jsonData = "{\"username\":\"" + response.getUsuario().getUsername() + "\", \"date\":\""
						+ new Date() + "\"}";

				String hash = Base64.getEncoder().encodeToString(jsonData.getBytes());

				try {
					mailService.sendEmail(this.MAIL_FROM, response.getUsuario().getUsername(), this.SUBJECT_MAIL,
							"https://heyme.com.gt/#/login?id=" + hash, MAIL_TEMPLATE_CONFIRM);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/update")
	public ListenableFuture<ResponseEntity<UsuarioResponse>> actualizarUsuario(@RequestBody UsuarioRequest input) {

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
		} else {
			Usuario usuario = this.usuarioService.findById(input.getDatos().getIdUsuario());
			usuario.setNombres(input.getDatos().getNombres());
			usuario.setApellidos(input.getDatos().getApellidos());
			usuario.setTelefono(input.getDatos().getTelefono());
			usuario.setDireccion(input.getDatos().getDireccion());
			usuario.setRole(new Role(input.getDatos().getRole().getIdRole()));

			usuario = this.usuarioService.save(usuario);

			response.setUsuario(CUsuario.EntityToModel(usuario));
			response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/changePassword")
	public AsyncResult<ResponseEntity<CambioContrasenaResponse>> cambiarContrasena(
			@RequestBody CambioContrasenaRequest request) {

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

				if (this.passwordEncoder.matches(request.getContrasenaActual(), usuario.getPassword())) {

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

	@Async
	@PostMapping("/activateUser")
	public AsyncResult<ResponseEntity<UsuarioResponse>> activarUsuario(@RequestBody UsuarioRequest input) {
		LOG.info("METHOD: activarUsuario() --PARAMS: " + input);
		UsuarioResponse response = new UsuarioResponse();
		if (input.getDatos() == null || input.getDatos().getUsername() == null
				|| input.getDatos().getUsername().isEmpty()) {
			response.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			response.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else {

			boolean validBase64 = org.apache.commons.codec.binary.Base64.isBase64(input.getDatos().getUsername());

			if (validBase64) {

				byte[] decodeByte = Base64.getDecoder().decode(input.getDatos().getUsername());
				String decode = new String(decodeByte);

				String[] splitString = decode.split("\"");

				Usuario usuario = this.usuarioService.findByUsername(splitString[3].toString());
				if (usuario != null) {

					usuario = this.usuarioService.save(usuario);
					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					response.setUsuario(CUsuario.EntityToModel(usuario));

				} else {
					response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
					response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
					response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
				}
			} else {
				response.setCodigo(Response.INVALID_BASE64.getCodigo());
				response.setDescripcion(Response.INVALID_BASE64.getMessage());
				response.setIndicador(Response.INVALID_BASE64.getIndicador());
			}

		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/sendActivationMail")
	public AsyncResult<ResponseEntity<UsuarioResponse>> enviarCorreoActivacion(@RequestBody UsuarioRequest input)
			throws IOException {
		LOG.info("METHOD: enviarCorreoActivacion() --PARAMS: " + input);
		UsuarioResponse response = new UsuarioResponse();
		if (input.getDatos() == null || input.getDatos().getUsername() == null
				|| input.getDatos().getUsername().isEmpty()) {
			response.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			response.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				Usuario usuarioNuevo = this.usuarioService.findByUsername(input.getDatos().getUsername());

				if (usuarioNuevo == null) {
					response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
					response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
					response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
				} else {

					if (usuario.getEmpresa().getIdEmpresa().compareTo(usuarioNuevo.getEmpresa().getIdEmpresa()) == 0) {
						String jsonData = "{\"username\":\"" + input.getDatos().getUsername() + "\", \"date\":\""
								+ new Date() + "\"}";
						String hash = Base64.getEncoder().encodeToString(jsonData.getBytes());

//						String textTemplate = this.fileStorageService.loadFileAsString(this.MAIL_TEMPLATE_CONFIRM)
//								.replace("{token}", hash);
//						System.out.println("------------ textTemplate " + textTemplate);

//						mailService.sendMail(this.MAIL_FROM, usuarioNuevo.getUsername(), this.SUBJECT_MAIL, textTemplate);
						try {
							this.mailService.sendEmail(this.MAIL_FROM, usuarioNuevo.getUsername(), this.SUBJECT_MAIL,
									hash, MAIL_TEMPLATE_CONFIRM);
						} catch (MessagingException | IOException e) {
							e.printStackTrace();

							LOG.info("error al enviar correo: " + e.getMessage());
						}

						response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
						response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
						response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					} else {
						response.setCodigo(Response.USERS_NOT_ARE_SAME_COMPANY.getCodigo());
						response.setDescripcion(Response.USERS_NOT_ARE_SAME_COMPANY.getMessage());
						response.setIndicador(Response.USERS_NOT_ARE_SAME_COMPANY.getIndicador());
					}
				}
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
}
