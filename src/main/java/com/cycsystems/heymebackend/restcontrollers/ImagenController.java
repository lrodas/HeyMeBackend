package com.cycsystems.heymebackend.restcontrollers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IFileStorageService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.ImagenResponse;
import com.cycsystems.heymebackend.util.Constants;

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
			output.setCodigo("0015");
			output.setDescripcion("Debes enviar una imagen a guardar");
			output.setIndicador("ERROR");
		} else if (imagen == null || imagen.isEmpty()) {
			output.setCodigo("0014");
			output.setDescripcion("Debes enviar el id del usuario para guardar la imagen");
			output.setIndicador("ERROR");
		} else {
			
			Usuario usuario = this.usuarioService.findById(id);
			
			if (usuario != null) {				
				try {
					nombreArchivo = this.fileStorage.storeFile(imagen, id);
				} catch (IOException e) {
					LOG.info("A ocurrido un error al guardar la imagen: " + e);
					
					output.setCodigo("0016");
					output.setDescripcion("Debes enviar una imagen a guardar");
					output.setIndicador("ERROR");
				}
				
				if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
					usuario.setImg(nombreArchivo);
					this.usuarioService.save(usuario);
					
					output.setCodigo("0000");
					output.setDescripcion("La imagen del usuario fue actualizada exitosamente");
					output.setIndicador("SUCCESS");
					output.setImg(nombreArchivo);
				}
			} else {
				output.setCodigo("0017");
				output.setDescripcion("El usuario no existe, por favor verificar");
				output.setIndicador("ERROR");
			}
			
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@RequestMapping(value = "/view/{name}", method = RequestMethod.GET, produces = "image/jpg")
	public ListenableFuture<ResponseEntity<Resource>> obtenerImagen(@PathVariable("name") String nombre) {

		LOG.info("METHOD: obtenerImagen() --PARAMS: nombre: " + nombre);
		
		Resource loader = this.fileStorage.loadFileAsResource(nombre);
		return new AsyncResult<>(new ResponseEntity<Resource>(loader, HttpStatus.OK));
	}
}
