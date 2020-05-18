package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.convert.CContacto;
import com.cycsystems.heymebackend.convert.CPais;
import com.cycsystems.heymebackend.convert.CProvincia;
import com.cycsystems.heymebackend.input.ContactoRequest;
import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Grupo;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IContactoService;
import com.cycsystems.heymebackend.models.service.IGrupoService;
import com.cycsystems.heymebackend.models.service.IPaisService;
import com.cycsystems.heymebackend.models.service.IProvinciaService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.ContactoResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
import com.cycsystems.heymebackend.util.Util;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.cycsystems.heymebackend.convert.CContacto.EntityToModel;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/contact")
public class ContactoController {

	private Logger LOG = LogManager.getLogger(ContactoController.class);
	
	private final IProvinciaService provinciaService;
	
	private final IContactoService contactoService;
	
	private final IUsuarioService usuarioService;

	private final IPaisService paisService;

	private final IGrupoService grupoService;

	@Autowired
	public ContactoController(IProvinciaService provinciaService,
							  IContactoService contactoService,
							  IUsuarioService usuarioService,
							  IPaisService paisService,
							  IGrupoService grupoService) {
		this.provinciaService = provinciaService;
		this.contactoService = contactoService;
		this.usuarioService = usuarioService;
		this.paisService = paisService;
		this.grupoService = grupoService;
	}

	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerTodos(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactos() --PARAMS: " + input);
		ContactoResponse output = new ContactoResponse();

		Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

		if (usuario == null) {
			output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
			output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
			output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
		} else {
			List<com.cycsystems.heymebackend.common.Contacto> contactos = this.contactoService
					.findAll(usuario.getEmpresa().getIdEmpresa())
					.stream()
					.map(CContacto::EntityToModel)
					.collect(Collectors.toList());

			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			output.setContactos(contactos);
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findById")
	public ListenableFuture<ResponseEntity<?>> buscarContactoPorId(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: buscarcontactoPorId() --PARAMS: " + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto() == null) {
			output.setCodigo(Response.CONTACT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getIdContacto() == null || input.getContacto().getIdContacto() <= 0) {
			output.setCodigo(Response.CONTACT_ID_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_ID_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_ID_NOT_EMPTY.getIndicador());
		} else {
			com.cycsystems.heymebackend.common.Contacto contacto = EntityToModel(this.contactoService
						.findById(input.getContacto().getIdContacto()));
			
			if (contacto == null) {
				output.setCodigo(Response.CONTACT_NOT_EXIST.getCodigo());
				output.setDescripcion(Response.CONTACT_NOT_EXIST.getMessage());
				output.setIndicador(Response.CONTACT_NOT_EXIST.getIndicador());
			} else {
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setContacto(contacto);
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByStatus")
	public ListenableFuture<ResponseEntity<?>> obtenerContactoPorEstado(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactoPorEstado() --PARAMS: contactoRequest:" + input);
		
		ContactoResponse output = new ContactoResponse();

		if (input.getContacto() == null) {
			output.setCodigo(Response.CONTACT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getEstado() == null) {
			output.setCodigo(Response.CONTACT_STATUS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_STATUS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_STATUS_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				List<com.cycsystems.heymebackend.common.Contacto> contactos = this.contactoService
						.findByStatus(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getEstado())
						.stream()
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setContactos(contactos);
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByCreationDate")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactoPorFecha(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContactoPorFecha() --PARAMS: ContactoRequest: " + input);
		
		ContactoResponse output = new ContactoResponse();
		Date fechaInicio;
		Date fechaFin;

		if (input.getFechaInicio() == null) {
			output.setCodigo(Response.START_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.START_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.START_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaFin() == null) {
			output.setCodigo(Response.END_DATE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.END_DATE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.END_DATE_NOT_EMPTY.getIndicador());
		} else if (input.getFechaInicio().after(input.getFechaFin())) {
			output.setCodigo(Response.START_DATE_BEFORE_END_DATE.getCodigo());
			output.setDescripcion(Response.START_DATE_BEFORE_END_DATE.getMessage());
			output.setIndicador(Response.START_DATE_BEFORE_END_DATE.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				fechaInicio = Util.mapDate(input.getFechaInicio());

				fechaFin = Util.mapDate(input.getFechaFin());

				List<com.cycsystems.heymebackend.common.Contacto> contactos = this.contactoService
						.findByCreationDate(usuario.getEmpresa().getIdEmpresa(), fechaInicio, fechaFin)
						.stream()
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList());

				output.setContactos(contactos);
				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/findByName")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactoPorNombre(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: obtenerContacto() --PARAMS: ContactoRequest: " + input);
		ContactoResponse output = new ContactoResponse();

		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo(Response.CONTACT_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NAME_NOT_EMPTY.getIndicador());
		} else {
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				List<com.cycsystems.heymebackend.common.Contacto> contactos = this.contactoService
						.findByName(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getNombre())
						.stream()
						.filter(contacto -> usuario.getEmpresa().getIdEmpresa().compareTo(contacto.getEmpresa().getIdEmpresa()) == 0)
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList());

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setContactos(contactos);
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/saveAll")
	public ListenableFuture<ResponseEntity<ContactoResponse>> guardarContactos(@RequestBody ContactoRequest input) {

		LOG.info("METHOD: guardarContactos() --PARAMS: ContactoRequest:" + input);
		ContactoResponse output = new ContactoResponse();

		if (input.getContactos() == null || input.getContactos().isEmpty()) {
			output.setCodigo(Response.CONTACT_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NOT_EMPTY.getIndicador());
		} else {
			List<String> errores = new ArrayList<>();
			for (com.cycsystems.heymebackend.common.Contacto contactoModel: input.getContactos()) {
				if (contactoModel.getNombre() == null || contactoModel.getNombre().isEmpty()) {
					errores.add(Response.CONTACT_NAME_NOT_EMPTY.getMessage());
				}
				if (contactoModel.getApellido() == null || contactoModel.getApellido().isEmpty()) {
					errores.add(Response.CONTACT_LAST_NAME_NOT_EMPTY.getMessage());
				}
				if (contactoModel.getDireccion() == null || contactoModel.getDireccion().isEmpty()) {
					errores.add(Response.CONTACT_ADDRESS_NOT_EMPTY.getMessage());
				}
				if (contactoModel.getTelefono() == null || contactoModel.getTelefono().isEmpty()) {
					errores.add(Response.CONTACT_PHONE_NOT_EMPTY.getMessage());
				}
				if (contactoModel.getPais() == null || contactoModel.getPais().getIdPais() == null ||
						contactoModel.getPais().getIdPais() <= 0) {
					errores.add(Response.COUNTRY_ID_EMPTY.getMessage());
				}
				if (contactoModel.getProvincia() != null && contactoModel.getProvincia().getIdProvincia() != null) {
					if (!this.provinciaService.existsById(contactoModel.getProvincia().getIdProvincia())) {
						errores.add(Response.CONTACT_REGION_NOT_EXIST.getMessage());
					}
				}
				if (contactoModel.getPais() != null &&
						contactoModel.getPais().getIdPais() != null &&
						!this.paisService.existById(contactoModel.getPais().getIdPais())) {
					errores.add(Response.COUNTRY_NOT_EXIST.getMessage());
				}

				if (contactoModel.getGrupo() != null &&
						contactoModel.getGrupo().getIdGrupo() != null &&
						!this.grupoService.existById(contactoModel.getGrupo().getIdGrupo())) {
					errores.add(Response.GROUP_NOT_EXIST.getMessage());
				}

				if (contactoModel.getGrupo() != null) {
					if (contactoModel.getGrupo().getIdGrupo() != null) {
						if (!this.grupoService.existById(contactoModel.getGrupo().getIdGrupo())) {
							errores.add(Response.GROUP_NOT_EXIST.getMessage());
						}
					} else {
						if (contactoModel.getGrupo().getNombre() == null || contactoModel.getGrupo().getNombre().trim().isEmpty()) {
							errores.add(Response.GROUP_NAME_NOT_EMPTY.getMessage());
						}
					}
				}
			}

			if (!errores.isEmpty()) {
				output.setCodigo(Response.CONTACT_SAVE_ERRORS.getCodigo());
				output.setDescripcion(errores.toString());
				output.setIndicador(Response.CONTACT_SAVE_ERRORS.getIndicador());
			} else {
				Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

				if (usuario == null) {
					output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
					output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
					output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
				} else {

					List<Contacto> contactos = new ArrayList<>();
					for (com.cycsystems.heymebackend.common.Contacto contactoModel: input.getContactos()) {
						Contacto contacto = new Contacto();

						if (contactoModel.getProvincia() != null && contactoModel.getProvincia().getIdProvincia() != null) {
							contacto.setProvincia(CProvincia.ModelToEntity(contactoModel.getProvincia()));
						}
						contacto.setPais(CPais.ModelToEntity(contactoModel.getPais()));
						contacto.setNombre(contactoModel.getNombre());
						contacto.setApellido(contactoModel.getApellido());
						contacto.setDireccion(contactoModel.getDireccion());
						contacto.setEmail(contactoModel.getEmail());
						contacto.setTelefono(contactoModel.getTelefono());
						contacto.setEstado(contactoModel.getEstado());
						contacto.setUsuario(usuario);
						contacto.setEmpresa(usuario.getEmpresa());

						if (contactoModel.getGrupo() != null) {
							if (contactoModel.getGrupo().getIdGrupo() != null) {

								Grupo grupo = this.grupoService.findById(contactoModel.getGrupo().getIdGrupo());
								contacto.setGrupo(grupo);
							} else {
								if (contactoModel.getGrupo().getNombre() != null && !contactoModel.getGrupo().getNombre().trim().isEmpty()) {
									Grupo grupo = new Grupo();
									grupo.setNombre(contactoModel.getGrupo().getNombre());
									grupo.setEmpresa(usuario.getEmpresa());
									contacto.setGrupo(grupo);
								}
							}
						} else {
							contacto.setGrupo(null);
						}
						contactos.add(contacto);
					}
					LOG.info(contactos);
					contactos = this.contactoService.saveAll(contactos);

					output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					output.setContactos(contactos
						.stream()
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList()));
				}
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<ContactoResponse>> guardarContacto(@RequestBody ContactoRequest input) {
		
		LOG.info("METHOD: guardarContacto() --PARAMS: ContactoRequest:" + input);
		ContactoResponse output = new ContactoResponse();
		
		if (input.getContacto().getNombre() == null || input.getContacto().getNombre().isEmpty()) {
			output.setCodigo(Response.CONTACT_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getApellido() == null || input.getContacto().getApellido().isEmpty()) {
			output.setCodigo(Response.CONTACT_LAST_NAME_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_LAST_NAME_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_LAST_NAME_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getDireccion() == null || input.getContacto().getDireccion().isEmpty()) {
			output.setCodigo(Response.CONTACT_ADDRESS_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_ADDRESS_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_ADDRESS_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getTelefono() == null || input.getContacto().getTelefono().isEmpty()) {
			output.setCodigo(Response.CONTACT_PHONE_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.CONTACT_PHONE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.CONTACT_PHONE_NOT_EMPTY.getIndicador());
		} else if (input.getContacto().getPais() == null || input.getContacto().getPais().getIdPais() == null ||
					input.getContacto().getPais().getIdPais() <= 0) {
			output.setCodigo(Response.COUNTRY_ID_EMPTY.getCodigo());
			output.setDescripcion(Response.COUNTRY_ID_EMPTY.getMessage());
			output.setIndicador(Response.COUNTRY_ID_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Contacto contacto = new Contacto();
				if (input.getContacto().getProvincia() != null && input.getContacto().getProvincia().getIdProvincia() != null) {
					com.cycsystems.heymebackend.models.entity.Provincia provincia = this.provinciaService.findById(input.getContacto().getProvincia().getIdProvincia());

					if (provincia == null) {
						output.setCodigo(Response.CONTACT_REGION_NOT_EXIST.getCodigo());
						output.setDescripcion(Response.CONTACT_REGION_NOT_EXIST.getMessage());
						output.setIndicador(Response.CONTACT_REGION_NOT_EXIST.getIndicador());

						return new AsyncResult<>(ResponseEntity.ok(output));
					} else {
						contacto.setProvincia(provincia);
					}
				}

				com.cycsystems.heymebackend.models.entity.Pais pais = this.paisService.findCountryById(input.getContacto().getPais().getIdPais());

				if (pais == null) {
					output.setCodigo(Response.COUNTRY_NOT_EXIST.getCodigo());
					output.setDescripcion(Response.COUNTRY_NOT_EXIST.getMessage());
					output.setIndicador(Response.COUNTRY_NOT_EXIST.getIndicador());
					return new AsyncResult<>(ResponseEntity.ok(output));
				} else {
					contacto.setPais(pais);
				}

				if (input.getContacto().getIdContacto() != null) {
					contacto.setIdContacto(input.getContacto().getIdContacto());
				}
				contacto.setNombre(input.getContacto().getNombre());
				contacto.setApellido(input.getContacto().getApellido());
				contacto.setDireccion(input.getContacto().getDireccion());
				contacto.setEmail(input.getContacto().getEmail());
				contacto.setTelefono(input.getContacto().getTelefono());
				contacto.setEstado(input.getContacto().getEstado());
				contacto.setUsuario(usuario);
				contacto.setEmpresa(usuario.getEmpresa());

				if (input.getContacto().getGrupo() != null) {
					if (input.getContacto().getGrupo().getIdGrupo() != null) {
						Grupo grupo = this.grupoService.findById(input.getContacto().getGrupo().getIdGrupo());

						if (grupo != null) {
							contacto.setGrupo(grupo);
						} else {
							output.setCodigo(Response.GROUP_NOT_EXIST.getCodigo());
							output.setDescripcion(Response.GROUP_NOT_EXIST.getMessage());
							output.setIndicador(Response.GROUP_NOT_EXIST.getIndicador());
							return new AsyncResult<>(ResponseEntity.ok(output));
						}
					} else {
						if (input.getContacto().getGrupo().getNombre() == null || input.getContacto().getGrupo().getNombre().trim().isEmpty()) {
							output.setCodigo(Response.GROUP_NAME_NOT_EMPTY.getCodigo());
							output.setDescripcion(Response.GROUP_NAME_NOT_EMPTY.getMessage());
							output.setIndicador(Response.GROUP_NAME_NOT_EMPTY.getIndicador());
							return new AsyncResult<>(ResponseEntity.ok(output));
						} else {
							Grupo grupo = new Grupo();
							grupo.setNombre(input.getContacto().getGrupo().getNombre());
							grupo.setEmpresa(usuario.getEmpresa());
							contacto.setGrupo(grupo);
						}
					}
				}
				LOG.info(contacto);
				contacto = this.contactoService.save(contacto);

				output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				output.setContacto(EntityToModel(contacto));
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/findByGroupName")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactosPorNombreGrupo(@RequestBody ContactoRequest input) {
		LOG.info("METHOD: obtenerContacotsPorNombreGrupo() --PARAMS: " + input);
		ContactoResponse response = new ContactoResponse();

		if (input.getContacto() == null ||
				input.getContacto().getGrupo() == null ||
				input.getContacto().getGrupo().getNombre() == null ||
				input.getContacto().getGrupo().getNombre().isEmpty()) {
			response.setCodigo(Response.GROUP_NAME_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.GROUP_NAME_NOT_EMPTY.getMessage());
			response.setIndicador(Response.GROUP_NAME_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			if (usuario == null) {
				response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {

				List<Contacto> contactos = this.contactoService.findByGroupName(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getGrupo().getNombre());
				response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
				response.setContactos(contactos
						.stream()
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList()));
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/findByGroup")
	public ListenableFuture<ResponseEntity<ContactoResponse>> obtenerContactosPorGrupo(@RequestBody ContactoRequest input) {
		LOG.info("METHOD: obtenerContactosPorGrupo() --PARAMS: " + input);
		ContactoResponse response = new ContactoResponse();

		if (input.getContacto() == null ||
				input.getContacto().getGrupo() == null ||
				input.getContacto().getGrupo().getIdGrupo() == null ||
				input.getContacto().getGrupo().getIdGrupo() <= 0) {
			response.setCodigo(Response.GROUP_ID_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.GROUP_ID_NOT_EMPTY.getMessage());
			response.setIndicador(Response.GROUP_ID_NOT_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Grupo grupo = this.grupoService.findById(input.getContacto().getGrupo().getIdGrupo());

				if (grupo == null) {
					response.setCodigo(Response.GROUP_NOT_EXIST.getCodigo());
					response.setDescripcion(Response.GROUP_NOT_EXIST.getMessage());
					response.setIndicador(Response.GROUP_NOT_EXIST.getIndicador());
				} else {

					List<Contacto> contactos = this.contactoService.findByGroup(usuario.getEmpresa().getIdEmpresa(), input.getContacto().getGrupo().getIdGrupo());

					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					response.setContactos(contactos
						.stream()
						.map(CContacto::EntityToModel)
						.collect(Collectors.toList()));
				}
			}
		}
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
}
