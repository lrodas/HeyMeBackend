package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
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

	@Autowired
	private IEmpresaService empresaService;
	
	@PostMapping("/upload/{id}")
	public ListenableFuture<ResponseEntity<?>> subirImagenes(
			@RequestParam("imagen") MultipartFile imagen,
			@PathVariable("id") String id) {
		
		LOG.info("METHOD: subirImagenes() --PARAMS: id: " + id);
		
		ImagenResponse output = new ImagenResponse();
		String nombreArchivo = "";

		if (imagen == null || imagen.isEmpty()) {
			output.setCodigo(Response.IMAGE_NOT_EMPTY_ERROR.getCodigo());
			output.setDescripcion(Response.IMAGE_NOT_EMPTY_ERROR.getMessage());
			output.setIndicador(Response.IMAGE_NOT_EMPTY_ERROR.getIndicador());
		} else if (id == null || id.isEmpty()) {
			output.setCodigo(Response.ID_USUARIO_ERROR.getCodigo());
			output.setDescripcion(Response.ID_USUARIO_ERROR.getMessage());
			output.setIndicador(Response.ID_USUARIO_ERROR.getIndicador());
		} else {
			if (id.split("_").length == 2) {
				Integer identificador = Integer.parseInt(id.split("_")[0]);
				String tipo = id.split("_")[1];
				LOG.info("ID: " + identificador + ", tipo: " + tipo);
				if (tipo.equalsIgnoreCase("empresa")) {
					LOG.info("Buscando empresa");
					Empresa empresa = this.empresaService.findById(identificador);
					LOG.info("Validando empresa: " + empresa);
					if (empresa != null) {
						nombreArchivo = storeFile(imagen, output, nombreArchivo, identificador, tipo);

						if (nombreArchivo != null && !nombreArchivo.isEmpty()) {

							empresa.setLogo(nombreArchivo);
							this.empresaService.save(empresa);

							output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
							output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
							output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
							output.setImg(nombreArchivo);
						}
					}
				} else {
					LOG.info("Entro en usuarios");
					Usuario usuario = this.usuarioService.findById(identificador);

					if (usuario != null) {
						nombreArchivo = storeFile(imagen, output, nombreArchivo, identificador, tipo);

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

			} else {
					output.setCodigo(Response.PARAMS_LENGTH_INVALID.getCodigo());
					output.setDescripcion(Response.PARAMS_LENGTH_INVALID.getMessage());
					output.setIndicador(Response.PARAMS_LENGTH_INVALID.getIndicador());
				}
		}
		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@RequestMapping(value = "/view/{name}", method = RequestMethod.GET, produces = "image/jpg")
	public ListenableFuture<ResponseEntity<Resource>> obtenerImagen(@PathVariable("name") String nombre) {

		LOG.info("METHOD: obtenerImagen() --PARAMS: nombre: " + nombre);

		String tipo = nombre.split("-")[0];
		Integer id = Integer.parseInt(nombre.split("-")[1]);
		String nombreImg = nombre.split("-")[1] + "-" + nombre.split("-")[2];
		LOG.info("parametros: tipo: " + tipo + ", id:" + id + ", nombreImg: " + nombreImg);
		Resource loader = this.fileStorage.loadFileAsResource(nombreImg, id, tipo);
		return new AsyncResult<>(new ResponseEntity<Resource>(loader, HttpStatus.OK));
	}

	private String storeFile(MultipartFile imagen, ImagenResponse output, String nombreArchivo, Integer identificador, String tipo) {
		try {
			nombreArchivo = this.fileStorage.storeFile(imagen, identificador, tipo);
		} catch (IOException e) {
			LOG.info("A ocurrido un error al guardar la imagen: " + e);

			output.setCodigo(Response.IMAGE_COULD_NOT_SAVE_ERROR.getCodigo());
			output.setDescripcion(Response.IMAGE_COULD_NOT_SAVE_ERROR.getMessage());
			output.setIndicador(Response.IMAGE_COULD_NOT_SAVE_ERROR.getIndicador());
		}
		return nombreArchivo;
	}
}
