package com.cycsystems.heymebackend.restcontrollers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.input.CambioContrasenaRequest;
import com.cycsystems.heymebackend.input.UsuarioRequest;
import com.cycsystems.heymebackend.models.entity.Genero;
import com.cycsystems.heymebackend.models.entity.Role;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.CambioContrasenaResponse;
import com.cycsystems.heymebackend.output.UsuarioResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/user")
public class UsuarioController {

	private Logger LOG = LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Value("${mail.from}")
	private String MAIL_FROM;
	
	// @Autowired
    // private MessageSource messageSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/resetPassword")
	public ListenableFuture<ResponseEntity<?>> restablecerContasena(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: restablecerContrasena() --PARAMS: usuarioRequest: " + input);
		UsuarioResponse response = new UsuarioResponse();
		
		if (input.getUsername() == null || input.getUsername().isEmpty()) {
			response.setCodigo("0001");
			response.setDescripcion("El nombre del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else {
			
			// mailService.sendMail(MAIL_FROM, input.getUsername(), subject, body);
		}
		
		return new AsyncResult<>(ResponseEntity.ok("")); 
	}
	
	@PostMapping("/save")
	public ListenableFuture<ResponseEntity<?>> guardarUsuario(@RequestBody UsuarioRequest input) {
		
		LOG.info("METHOD: guardarUsuario() --PARAMS: usuario: " + input);
		UsuarioResponse response = new UsuarioResponse();
		
		if (input.getNombres() == null || input.getNombres().isEmpty()) {
			response.setCodigo("0001");
			response.setDescripcion("El nombre del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else if (input.getApellidos() == null || input.getApellidos().isEmpty()) {
			response.setCodigo("0002");
			response.setDescripcion("El apellido del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else if (input.getDireccion() == null || input.getDireccion().isEmpty()) {
			response.setCodigo("0003");
			response.setDescripcion("La direccion del usuario es obligatoria");
			response.setIndicador("ERROR");
		} else if (input.getTelefono() == null || input.getTelefono().isEmpty()) { 
			response.setCodigo("0004");
			response.setDescripcion("La direccion del usuario es obligatoria");
			response.setIndicador("ERROR");
		} else if (input.getGenero() == null || input.getGenero().getIdGenero() == null || input.getGenero().getIdGenero() <= 0) {
			response.setCodigo("0005");
			response.setDescripcion("El genero del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else if (input.getRole() == null || input.getRole().getIdRole() == null || input.getRole().getIdRole() <= 0) {
			response.setCodigo("0006");
			response.setDescripcion("El role del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else if (input.getUsername() == null || input.getUsername().isEmpty()) {
			response.setCodigo("0007");
			response.setDescripcion("El correo del usuario es obligatorio");
			response.setIndicador("ERROR");
		} else if (input.getPassword() == null || input.getPassword().isEmpty()) {
			response.setCodigo("0008");
			response.setDescripcion("La contrasena del usuario es obligatoria");
			response.setIndicador("ERROR");
		} else {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(input.getIdUsuario());
			usuario.setNombres(input.getNombres());
			usuario.setApellidos(input.getApellidos());
			usuario.setTelefono(input.getTelefono());
			usuario.setDireccion(input.getDireccion());
			usuario.setGenero(new Genero(input.getGenero().getIdGenero(), input.getGenero().getDescripcion()));
			usuario.setRole(new Role(input.getRole().getIdRole()));
			usuario.setUsername(input.getUsername());
			usuario.setPassword(this.passwordEncoder.encode(input.getPassword()));
			usuario.setImg(input.getImg());
			usuario.setEnabled(input.getEnabled());
			
			response.setUsuario(this.usuarioService.save(usuario));
			
			response.getUsuario().setPassword(":-)");
			
			response.setCodigo("0000");
			response.setDescripcion("Usuario guardado exitosamente");
			response.setIndicador("EXITO");
			
		}
				
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
	
	@PostMapping("/changePassword")
	public AsyncResult<ResponseEntity<?>> cambiarContrasena(@RequestBody CambioContrasenaRequest request) {
		
		LOG.info("METHOD: cambiarContrasena() --PARAMS: CambioContrasenaRequest: " + request);
		CambioContrasenaResponse response = new CambioContrasenaResponse();
		
		if (request.getContrasenaActual() == null || request.getContrasenaActual().isEmpty()) {
			response.setCodigo("0009");
			response.setDescripcion("Debe enviar la contrasena para la validacion");
			response.setIndicador("ERROR");
		} else if (request.getNuevaContrasena() == null || request.getNuevaContrasena().isEmpty()) {
			response.setCodigo("0010");
			response.setDescripcion("Debe enviar la nueva contrasena para la validacion");
			response.setIndicador("ERROR");
		} else if (request.getIdUsuario() == null || request.getIdUsuario() <= 0) {
			response.setCodigo("0011");
			response.setDescripcion("Debe especificar el usuario");
			response.setIndicador("ERROR");
		} else {
			
			Usuario usuario = this.usuarioService.findById(request.getIdUsuario());
			
			if (usuario != null) {
				
				if (this.passwordEncoder.matches(request.getContrasenaActual(), usuario.getPassword()) ) {
					
					usuario.setPassword(request.getNuevaContrasena());
					this.usuarioService.save(usuario);
					
					response.setCodigo("0000");
					response.setDescripcion("Contrasena actualizada exitosamente");
					response.setIndicador("SUCCESS");
				} else {
					response.setCodigo("0012");
					response.setDescripcion("La contrasena es incorrecta");
					response.setIndicador("ERROR");
				}
			} else {
				response.setCodigo("0013");
				response.setDescripcion("El usuario no existe");
				response.setIndicador("ERROR");
			}
		}
		
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
}
