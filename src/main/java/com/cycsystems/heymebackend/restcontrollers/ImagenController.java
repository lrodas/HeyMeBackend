package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IFileStorageService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.ImagenResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/image")
public class ImagenController {

	private Logger LOG = LogManager.getLogger(ImagenController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IFileStorageService fileStorage;
	
	@PostMapping("/upload/{id}")
	public ListenableFuture<ResponseEntity<?>> subirImagenes(
			@RequestParam("imagen") MultipartFile imagen,
			@PathVariable("id") Integer id) {
		
		LOG.info("METHOD: subirImagenes() --PARAMS: id: " + id);
		
		ImagenResponse output = new ImagenResponse();
		String nombreArchivo = "";
		
		if (imagen == null || imagen.isEmpty()) {
			output.setCodigo(Response.IMAGE_NOT_EMPTY_ERROR.getCodigo());
			output.setDescripcion(Response.IMAGE_NOT_EMPTY_ERROR.getMessage());
			output.setIndicador(Response.IMAGE_NOT_EMPTY_ERROR.getIndicador());
		} else if (imagen == null || imagen.isEmpty()) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {
			
			Usuario usuario = this.usuarioService.findById(id);
			
			if (usuario != null) {				
				try {
					nombreArchivo = this.fileStorage.storeFile(imagen, id);
				} catch (IOException e) {
					LOG.info("A ocurrido un error al guardar la imagen: " + e);
					
					output.setCodigo(Response.IMAGE_COULD_NOT_SAVE_ERROR.getCodigo());
					output.setDescripcion(Response.IMAGE_COULD_NOT_SAVE_ERROR.getMessage());
					output.setIndicador(Response.IMAGE_COULD_NOT_SAVE_ERROR.getIndicador());
				}
				
				if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
					usuario.setImg(nombreArchivo);
					this.usuarioService.save(usuario);
					
					output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
					output.setImg(nombreArchivo);
				}
			} else {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			}
			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@RequestMapping(value = "/view/{name}", method = RequestMethod.GET, produces = "image/jpg")
	public ListenableFuture<ResponseEntity<Resource>> obtenerImagen(@PathVariable("name") String nombre) {

		LOG.info("METHOD: obtenerImagen() --PARAMS: nombre: " + nombre);
		
		Integer idUsuario = Integer.parseInt(nombre.split("-")[0]);
		
		Resource loader = this.fileStorage.loadFileAsResource(nombre, idUsuario);
		return new AsyncResult<>(new ResponseEntity<Resource>(loader, HttpStatus.OK));
	}
}
